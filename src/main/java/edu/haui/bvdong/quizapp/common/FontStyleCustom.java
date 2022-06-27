package edu.haui.bvdong.quizapp.common;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

import java.io.File;

public class FontStyleCustom {

    public FontStyleCustom() {
    }
    public static final int NORMAL = 0;
    public static final int BOLD = 1;
    public static final int ITALIC = 2;

    //FONT NORMAL
//    customFont(float size,int style, BaseColor color)
    public static Font SIZE_28 = FontFamilyCustom.customFont(28,NORMAL,BaseColor.DARK_GRAY);

    public static Font SIZE_26 =FontFamilyCustom.customFont(26,NORMAL,BaseColor.DARK_GRAY);
    public static Font SIZE_21 = FontFamilyCustom.customFont(21,NORMAL,BaseColor.DARK_GRAY);
    public static Font SIZE_18 = FontFamilyCustom.customFont(18,NORMAL,BaseColor.DARK_GRAY);
    public static Font SIZE_16 = FontFamilyCustom.customFont(16,NORMAL,BaseColor.DARK_GRAY);
    public static Font SIZE_14 = FontFamilyCustom.customFont(14,NORMAL,BaseColor.DARK_GRAY);
    public static Font SIZE_12 = FontFamilyCustom.customFont(12,NORMAL,BaseColor.DARK_GRAY);


    //FONT BOLD
    public static Font SIZE_28_BOLD = FontFamilyCustom.customFont(28,BOLD,BaseColor.DARK_GRAY);
    public static Font SIZE_26_BOLD = FontFamilyCustom.customFont(26,BOLD,BaseColor.DARK_GRAY);
    public static Font SIZE_21_BOLD = FontFamilyCustom.customFont(21,BOLD,BaseColor.DARK_GRAY);
    public static Font SIZE_18_BOLD = FontFamilyCustom.customFont(18,BOLD,BaseColor.DARK_GRAY);
    public static Font SIZE_16_BOLD = FontFamilyCustom.customFont(16,BOLD,BaseColor.DARK_GRAY);
    public static Font SIZE_14_BOLD = FontFamilyCustom.customFont(14,BOLD,BaseColor.DARK_GRAY);
    public static Font SIZE_12_BOLD = FontFamilyCustom.customFont(12,BOLD,BaseColor.DARK_GRAY);

    //FONT ITALIC
    public static Font SIZE_18_ITALIC =FontFamilyCustom.customFont(18,ITALIC,BaseColor.DARK_GRAY);

}
