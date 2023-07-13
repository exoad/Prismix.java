// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.prismix;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.TimerTask;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme;
import com.formdev.flatlaf.util.SystemInfo;
import com.jackmeng.ansicolors.jm_Ansi;
import com.jackmeng.prismix.stl.extend_stl_Colors;
import com.jackmeng.prismix.user.use_LSys;
import com.jackmeng.prismix.ux.gui_XErr;
import com.jackmeng.prismix.ux.gui_XInf;
import com.jackmeng.prismix.ux.ux_Theme;
import com.jackmeng.prismix.ux.__ux;
import com.jackmeng.prismix.ux.gui_XErr.Err_CloseState;
import com.jackmeng.prismix.ux.ux_Theme.ThemeType;
import com.jackmeng.stl.stl_Chrono;
import com.jackmeng.stl.stl_In;
import com.jackmeng.stl.stl_Str;
import com.jackmeng.stl.stl_Wrap;

import static com.jackmeng.prismix._1const.val;
import static com.jackmeng.prismix.user.use_Map.*;

/**
 * Color Palette Program Entry Point Class
 *
 * @author Jack Meng
 */
public class jm_Prismix
{

  // ! Any IO that happens here should be deemed important and not purely for
  // debug purposes!

  public static final AtomicLong time = new AtomicLong(System.currentTimeMillis());
  public static final long _VERSION_ = 2023_07_01L; // YYYY_MM_DD of the closest month
  public static final boolean IS_UNSTABLE = true;
  public static final PrintStream IO = System.out;
  static
  {
    _1const.shutdown_hook(() -> System.out.println(
        "=====================================================\n=====================================================\n\t\t\tGOING DOWN FOR SHUTDOWN\n=====================================================\n====================================================="));
    /*------------------------------------------------------------------------------------------------------- /
    / System.out.println(                                                                                     /
    /     "==Prismix==\nGUI Color Picker and palette creator\nCopyright (C) Jack Meng (exoad) 2023\nEnjoy!"); /
    /--------------------------------------------------------------------------------------------------------*/

    System.setOut(new PrintStream(new OutputStream() {
      @Override public void write(byte[] buffer, int offset, int length)
      {
        log("SYS", new String(buffer, offset, length));
      }

      @Override public void write(int b) throws IOException
      {
        write(new byte[] { (byte) b }, 0, 1);
      }
    }));

    use_LSys.init();

    System.setErr(System.out);

    val.put_("debug_gui", parse_Bool, new Object[] { Bool, "false", type_Bool,
        "Draw the GUI differently in order to debug layout issues and other graphical issues." });
    val.put_("soft_debug", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "Enable basic debug layers, like CLI debug, and more." });
    val.put_("smart_gui", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "Uses a hide and show paint schema instead of showing and painting." });
    val.put_("use_current_dir", parse_Bool,
        new Object[] { Bool, "true", type_Bool,
            "Uses the current directory of the program instead of HOME for storage." });
    val.put_("suggestions_sorted", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "Try to make sure the colors in a color picker are sorted (especially for suggestions)" });
    val.put_("suggestions_sort_light_to_dark", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "When suggestions_sorted is set to true, use lightest to darkest sorting, else if false, use darkest to lightest." });
    val.put_("containers_rounded", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "Used to determine whether to use rounded components or not. (For that eye candy ^_^)" });
    val.put_("stoopid_sliders", parse_Bool, new Object[] { Bool, "false", type_Bool,
        "Determines whether sliders should only wait till they come to rest to dispatch their value or dispatch a value everytime they move. Requires a restart!" });
    val.put_("queued_random_color", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "Makes the random color button use a queue system with several extra buttons for more controlled generation." });
    val.put_("descriptive_labels", parse_Bool, new Object[] {
        Bool, "false", type_Bool,
        "Makes certain labels on the UI more descriptive. For example, swapping \">\" out for \"Next\"."
    });
    val.put_("shush_info_dialogs", parse_Bool, new Object[] {
        Bool, "false", type_Bool,
        "Stops information dialogs from appearing; however, error and exception logs will still be launched."
    });
    val.put_("compact_suggestions_layout",
        parse_StrBound(new String[] { "compact", "vertical", "horizontal" }, "compact"), new Object[] { StrBound,
            "compact", new String[] { "compact", "vertical", "horizontal" },
            "Changes the layout styling of the color gallery in 3 formats: compact, vertical, and horizontal."
        });
    val.put_("dark_mode", parse_Bool, new Object[] { Bool, "true", type_Bool,
        "Use dark mode or light mode for the UI mode (true = light else dark)" });
    val.put_("developer_buttons", parse_Bool, new Object[] { Bool, "false", type_Bool,
        "Add a few buttons in the top right for managing garbage collection and refresh the UI." });
    val.put_("use_theme_based_tooling", parse_Bool, new Object[] { Bool, "false", type_Bool,
        "Makes certain color portrayals based on the theme instead of a de facto representation. For example \"RED\" in RGB can be hued for a slider if this is set to true." });
    use_LSys.load_map(_1const.val.name.replace("\\s+", "%"), _1const.val::set_property);

    /*-------------------------------------------------------------------------------------- /
    / final StringBuilder sb = new StringBuilder();                                          /
    / System.getProperties().forEach((key, value) -> sb.append(key + " = " + value + "\n")); /
    / System.out.println("All initialized properties:\n" + sb.toString());                   /
    /---------------------------------------------------------------------------------------*/
    try
    {
      System.setProperty("sun.java2d.opengl", "True");
      System.setProperty("sun.java2d.trace", "count");
      if (SystemInfo.isLinux || SystemInfo.isWindows_10_orLater)
      {
        System.setProperty("flatlaf.useWindowDecorations", "true");
        System.setProperty("flatlaf.menuBarEmbedded", "true");
        JFrame.setDefaultLookAndFeelDecorated(true);
      }
      else if (SystemInfo.isMacOS)
        System.setProperty("apple.awt.application.appearance", "system");
      /*-------------------------------------------------------------------------------------------------------------- /
      / UIManager.setLookAndFeel(_1const.DARK_MODE ? new FlatHighContrastIJTheme() : new FlatGrayIJTheme()); // or FlatGratIJTheme /
      /---------------------------------------------------------------------------------------------------------------*/
      UIManager
          .setLookAndFeel(
              Boolean.TRUE.equals((Boolean) _1const.val.parse("dark_mode").get()) ? new FlatHighContrastIJTheme()
                  : new FlatGrayIJTheme());
      UIManager.put("ScrollBar.background", extend_stl_Colors.awt_empty());
      UIManager.put("ScrollBar.showButtons", false);
      UIManager.put("JScrollPane.smoothScrolling", true);
      UIManager.put("SplitPaneDivider.gripDotCount", 4);
      UIManager.put("Component.focusedBorderColor", extend_stl_Colors.awt_empty());
      UIManager.put("Component.focusColor", extend_stl_Colors.awt_empty());
      UIManager.put("TabbedPane.tabSeparatorsFullHeight", false);
      UIManager.put("TabbedPane.showTabSeparators", true);

      for (Font f : new Font[] {
          Font.createFont(Font.TRUETYPE_FONT, _1const.fetcher.file("assets/font/FiraSans-Bold.ttf")).deriveFont(14F),
          Font.createFont(Font.TRUETYPE_FONT, _1const.fetcher.file("assets/font/FiraSans-BoldItalic.ttf"))
              .deriveFont(14F),
          Font.createFont(Font.TRUETYPE_FONT, _1const.fetcher.file("assets/font/FiraSans-Italic.ttf")).deriveFont(14F),
          Font.createFont(Font.TRUETYPE_FONT, _1const.fetcher.file("assets/font/FiraSans-Regular.ttf")).deriveFont(14F)
      })
      {
        Enumeration< ? > keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements())
        {
          Object key = keys.nextElement();
          Object value = UIManager.get(key);
          if (value instanceof FontUIResource orig)
          {
            Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
            UIManager.put(key, new FontUIResource(font));
          }
        }
      }

    } catch (final Exception e)
    {
      e.printStackTrace();
    }
    /*----------------------------------------------------------------------------- /
    / _lua.simple_load(_1const.fetcher.file("assets/lua/_.lua").getAbsolutePath()); /
    /------------------------------------------------------------------------------*/
    _1const.__init();
  }

  public static void main(final String... x) // !! fuck pre Java 11 users, fuck their dumb shit
  {
    try
    {

      _1const.add(__ux._ux, 10L);
      _1const.shutdown_hook(() -> use_LSys.write(_1const.val));
      ux_Theme._theme.theme(
          Boolean.TRUE.equals((Boolean) _1const.val.parse("dark_mode").get()) ? ThemeType.DARK : ThemeType.LIGHT);

      final stl_Wrap< stl_In > reader = new stl_Wrap<>(new stl_In(System.in));
      if (IS_UNSTABLE)
        gui_XInf.invoke(uwu.fowmat("assets/text/TEXT_prismix_unstable.html", IS_UNSTABLE + " | " + _VERSION_),
            "Caution");
      Runtime.getRuntime().addShutdownHook(
          (new Thread(
              () -> log("PRISMIX", "Alive for: " + (System.currentTimeMillis() - jm_Prismix.time.get())))));
      _1const.worker.scheduleAtFixedRate(new TimerTask() {
        @Override public void run()
        {
          final String str = reader.obj.nextln();
          log("I/O", "Received contract " + str);
        }
      }, 100L, 650L);
      log("PRISMIX", "Program took: " + (System.currentTimeMillis() - jm_Prismix.time.get()) + "ms to startup");

    } catch (Exception e)
    {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      gui_XErr.invoke(
          uwu.fowmat("assets/text/TEXT_external_issue.html", sw.toString()),
          "Exception!", "https://github.com/exoad/Prismix.java", Err_CloseState.EXIT);
    }
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
      if (!e.getMessage().equals(
          "Cannot invoke \"javax.swing.text.View.paint(java.awt.Graphics, java.awt.Shape)\" because \"this.view\" is null"))
      {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        gui_XErr.invoke(
            MessageFormat
                .format(use_LSys.read_all(_1const.fetcher.file("assets/text/TEXT_external_issue.html")),
                    sw.toString() + "\n\n\nTHREAD: " + t.toString()),
            "Exception!", "https://github.com/exoad/Prismix.java", Err_CloseState.EXIT);
      }
    });

  }

  public static void error_modal_wrap(Throwable e)
  {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    gui_XErr.invoke(
        MessageFormat
            .format(use_LSys.read_all(_1const.fetcher.file("assets/text/TEXT_external_issue.html")),
                sw.toString() + "\n\n\nType: " + e.getClass().getCanonicalName()),
        "Exception!", "https://github.com/exoad/Prismix.java", Err_CloseState.EXIT);
  }

  static final int MAX_LOG_NAME_LEN = 13;

  public static void debug(Object content)
  {
    for (String str : content.toString().split("\n"))
    {
      IO.println(
          jm_Ansi.make().bold().toString("| ") + jm_Ansi.make().red().white().toString("DEBUG")
              + jm_Ansi.make().bold().toString(" |") + " " + jm_Ansi.make().bold().toString("| ")
              + jm_Ansi.make().white().cyan()
                  .toString(stl_Chrono.format_time("HH:mm:ss:SSS", System.currentTimeMillis() - time.get()))
              + jm_Ansi.make().bold().toString(" |") + "\t"
              + jm_Ansi.make().cyan().bold().toString("||") + "\t\t" + str);
    }
  }

  public static void log(String name, String content)
  {
    name = name.length() > MAX_LOG_NAME_LEN ? name.substring(0, MAX_LOG_NAME_LEN)
        : name.length() < MAX_LOG_NAME_LEN ? name + (stl_Str.n_copies(MAX_LOG_NAME_LEN - name.length(), "_")) : name;
    for (String str : content.split("\n"))
    {
      IO.println(
          jm_Ansi.make().bold().toString("| ") + jm_Ansi.make().white_bg().red_fg().toString(name)
              + jm_Ansi.make().bold().toString(" |") + " " + jm_Ansi.make().bold().toString("| ")
              + jm_Ansi.make().white().magenta()
                  .toString(stl_Chrono.format_time("HH:mm:ss:SSS", System.currentTimeMillis() - time.get()))
              + jm_Ansi.make().bold().toString(" |") + "\t"
              + jm_Ansi.make().cyan().bold().toString("||") + "\t\t" + str);
    }
    /*------------------------------------------------------------------------------------------------------ /
    / IO.println(                                                                                            /
    /     jm_Ansi.make().bold().toString("| ") + jm_Ansi.make().white_bg().red_fg().toString(name)           /
    /         + jm_Ansi.make().bold().toString(" |") + " " + jm_Ansi.make().bold().toString("| ")            /
    /         + jm_Ansi.make().white().magenta()                                                             /
    /             .toString(stl_Chrono.format_time("HH:mm:ss:SSS", System.currentTimeMillis() - time.get())) /
    /         + jm_Ansi.make().bold().toString(" |") + "\t"                                                  /
    /         + jm_Ansi.make().cyan().bold().toString("||") + "\t\t" + content);                             /
    /-------------------------------------------------------------------------------------------------------*/
  }
}