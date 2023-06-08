// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix.ux;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.jackmeng.stl.stl_AnsiColors;
import com.jackmeng.stl.stl_AnsiMake;
import com.jackmeng.stl.stl_ListenerPool;

public class ux_Palette
{

  public static final PaletteManager PM = new PaletteManager();

  public static final class PaletteManager
      extends ArrayDeque< ux_Palette >
  {
    private transient ux_Palette history;

    public PaletteManager()
    {

    }

    public void hist_Add(final float[] color)
    {
      this.history.append(color);
    }

    public void hist_Rmf(final float[] color)
    {
      this.history.remove(color);
    }
  }

  private Set< List< Float > > colors_rgba;

  public static final class Palette_State
  {
    private final List< Float > payload;
    private final int startIndex, toIndex;

    /**
     * @param payload
     *          Payload
     * @param startIndex
     *          Where the payload started from (should always be original). If for
     *          append, then this value should be -1.
     * @param toIndex
     *          Where the payload should be moved to (-1 for removal,
     *          colors_rgba.length for add to top)
     */
    public Palette_State(final List< Float > payload, final int startIndex, final int toIndex)
    {
      this.payload = payload;
      this.startIndex = startIndex;
      this.toIndex = toIndex;
    }

    public List< Float > payload()
    {
      return this.payload;
    }

    public int start()
    {
      return this.startIndex;
    }

    public int end()
    {
      return this.toIndex;
    }
  }

  public final stl_ListenerPool< Palette_State > LISTENER_POOL;

  private String str_Name;
  private long palette_id;

  public ux_Palette(final int initial, final String name)
  {
    this.colors_rgba = new LinkedHashSet<>() {
      @Override public boolean add(final List< Float > e) // this set simulates the functionality that the duplicate gets
                                                    // appended to the end while the old value is deleted
      {
        final boolean added = super.add(e);
        if (!added)
        {
          super.remove(e);
          super.add(e);
        }
        return added;
      }
    };
    this.LISTENER_POOL = new stl_ListenerPool<>(name);
    this.LISTENER_POOL.add(x -> {
      System.out.println("[UX_PALETTE] Palette " + name + "[" + this.palette_id + "] received operation: " + x);
      return (Void) null;
    });
    this.str_Name = name;
    this.palette_id = this.hashCode() ^ this.str_Name.hashCode() * 81L;
    System.out.println(
        new stl_AnsiMake(stl_AnsiColors.GREEN_TXT, "[UX_PALETTE] ID: " + this.palette_id + " registered as: " + name));
  }

  public void append(final float[] e)
  {
    if (e == null || e.length < 4 || e.length > 4)
    {
      System.out.println("[UX_PALETTE] Palette" + this.hashCode() + " received an inproper constructed color array");
      return;
    }
    this.colors_rgba.add(ux_Palette.$toarr(e));
  }

  private static List< Float > $toarr(final float[] r) // similar to Arrays.asList but instead provides a writable ArrayList
  {
    final List< Float > temp = new ArrayList<>(r.length);
    for (final float e : r)
      temp.add(e);
    return temp;
  }

  private static float[] $toprim(final List< Float > r)
  {
    final float[] temp = new float[r.size()];
    for (int i = 0; i < r.size(); i++)
      temp[i] = r.get(i);
    return temp;
  }

  public boolean remove(final float[] e)
  {
    return this.colors_rgba.contains(ux_Palette.$toarr(e)) ? this.colors_rgba.remove(ux_Palette.$toarr(e)) : false;
  }

  public float[][] fetch()
  {
    final float[][] temp = new float[this.colors_rgba.size()][4];
    final int i = 0;
    this.colors_rgba.iterator().forEachRemaining(x -> temp[i] = ux_Palette.$toprim(x));
    return temp;
  }

  public String name()
  {
    return this.str_Name;
  }

  public void name(final String name)
  {
    this.str_Name = name;
  }

  public long id()
  {
    return this.palette_id;
  }
}