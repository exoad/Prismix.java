// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.
package com.jackmeng.clrplte;

import java.util.Timer;
import java.util.TimerTask;

import com.jackmeng.stl.stl_AssetFetcher;
import com.jackmeng.stl.stl_ListenerPool;
import com.jackmeng.stl.stl_Struct;
import com.jackmeng.stl.stl_AssetFetcher.assetfetcher_FetcherStyle;
import java.awt.Color;

public final class _1const
{
  public static Timer worker = new Timer("com-jackmeng-clrplte-worker01");
  public static stl_AssetFetcher fetcher = new stl_AssetFetcher(assetfetcher_FetcherStyle.WEAK);
  public static final String working_dir = System.getProperty("user.dir");
  /**
   * PAIR[0] = (java.awt.Color) Color payload
   * PAIR[1] = (java.lang.Boolean) Ignore Payload for storage
   */
  public static stl_ListenerPool< stl_Struct.struct_Pair< Color, Boolean > > COLOR_ENQ = new stl_ListenerPool<>(
      "current-processing-pool");
  {
    COLOR_ENQ.add(x -> {
      System.out.println("[COLOR_POOL] Enqueued another color for GUI elements to process: rgba(" + x.first.getRed()
          + "," + x.first.getGreen() + "," + x.first.getBlue() + "," + x.first.getAlpha() + ")");
      return (Void) null;
    });
  }

  public static synchronized void add(Runnable r, long delay, long rep_delay)
  {
    worker.scheduleAtFixedRate(new TimerTask() {
      @Override public void run()
      {
        r.run(); // ! Make sure this is not the current reference or it will produce a
                 // ! StackOverflow Error
      }
    }, delay, rep_delay);
  }
}