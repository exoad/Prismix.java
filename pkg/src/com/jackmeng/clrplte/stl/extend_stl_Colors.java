// Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

package com.jackmeng.clrplte.stl;

import java.awt.color.ColorSpace;

import com.jackmeng.clrplte._1const;

import java.awt.Color;

public final class extend_stl_Colors
{
  private extend_stl_Colors()
  {
  }

  public static int[] awt_colorspace_AllTypes()
  {
    return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 }; // we dont
                                                                                                           // know why
                                                                                                           // 10 isnt
                                                                                                           // here
  }

  public static Color awt_random_Color()
  {
    return new Color(_1const.RNG.nextFloat(), _1const.RNG.nextFloat(), _1const.RNG.nextFloat());
  }

  public static float[] rgbToHsv(float[] rgb)
  {
    return new float[] {
        Math.max(
            rgb[0] / 255F, Math
                .max(rgb[1] / 255F, rgb[2] / 255F)) == Math.min(rgb[0] / 255F,
                    Math.min(rgb[1] / 255F, rgb[2] / 255F)) ? 0
                        : Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F,
                            rgb[2] / 255F)) == rgb[0]
                                / 255F
                                    ? (60
                                        * ((rgb[1] / 255F - rgb[2] / 255F)
                                            / (Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))
                                                - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F))))
                                        + 360) % 360
                                    : Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) == rgb[1] / 255F
                                        ? (60
                                            * ((rgb[2] / 255F - rgb[0] / 255F) / (Math.max(rgb[0] / 255F,
                                                Math.max(rgb[1] / 255F, rgb[2] / 255F))
                                                - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F))))
                                            + 120) % 360
                                        : Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) == rgb[2]
                                            / 255F
                                                ? (60 * ((rgb[0] / 255F - rgb[1] / 255F) / (Math.max(rgb[0] / 255F,
                                                    Math.max(rgb[1] / 255F, rgb[2] / 255F))
                                                    - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F))))
                                                    + 240) % 360
                                                : -1,
        Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) == 0 ? 0
            : ((Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))
                - Math.min(rgb[0] / 255F, Math.min(rgb[1] / 255F, rgb[2] / 255F)))
                / Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F))) * 100,
        Math.max(rgb[0] / 255F, Math.max(rgb[1] / 255F, rgb[2] / 255F)) * 100 };
  }

  public static String awt_colorspace_NameMatch(ColorSpace e)
  {
    switch (e.getType()) {
      case 0:
        return "XYZ";
      case 1:
        return "Lab";
      case 2:
        return "Luv";
      case 3:
        return "YCbCr";
      case 4:
        return "Yxy";
      case 5:
        return "RGB";
      case 6:
        return "GRAY";
      case 7:
        return "HSV";
      case 8:
        return "HLS";
      case 9:
        return "CMYK";
      case 11:
        return "CMY";
      case 12:
        return "2CLR";
      case 13:
        return "3CLR";
      case 14:
        return "4CLR";
      case 15:
        return "5CLR";
      case 16:
        return "6CLR";
      case 17:
        return "7CLR";
      case 18:
        return "8CLR";
      case 19:
        return "9CLR";
      case 20:
        return "ACLR";
      case 21:
        return "BCLR";
      case 22:
        return "CCLR";
      case 23:
        return "DCLR";
      default:
        return "UNKNOWN";
    }
  }
}