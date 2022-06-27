package edu.haui.bvdong.quizapp.common;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;

@Component
public class FontFamilyCustom
{


    public FontFamilyCustom() {
    }

    public static Font customFont(float size,int style, BaseColor color)  {

        BaseFont baseFont = null;
        try {
            File fontFile = new ClassPathResource("static\\fonts\\TimeNewRM.ttf").getFile();
            baseFont = BaseFont.createFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        Font font = new Font(baseFont,size,style,color);
        return font;

    }
}
