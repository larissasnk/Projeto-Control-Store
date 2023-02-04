package com.projeto.gestaoloja.util;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class MaskCampos {
    DefaultFormatterFactory dff;

    public MaskFormatter maskTel(JFormattedTextField textField) throws ParseException {
        MaskFormatter mask;
        mask = new MaskFormatter("(##) ####-####");
        mask.setOverwriteMode(true);

        mask.setValidCharacters("0123456789");
        mask.setPlaceholderCharacter('_');
        dff = new DefaultFormatterFactory(mask);
        textField.setFormatterFactory(dff);
        return mask;
    }

    public MaskFormatter maskCpf(JFormattedTextField textField) throws ParseException {
        MaskFormatter mask;
        mask = new MaskFormatter("###.###.###-##");
        mask.setOverwriteMode(true);
        mask.setValidCharacters("0123456789");
        mask.setPlaceholderCharacter('_');
        dff = new DefaultFormatterFactory(mask);
        textField.setFormatterFactory(dff);
        return mask;
    }

    public MaskFormatter maskCnpj(JFormattedTextField textField) throws ParseException {
        MaskFormatter mask;
        mask = new MaskFormatter("##.###.###/####-##");
        mask.setOverwriteMode(true);
        mask.setValidCharacters("0123456789");
        mask.setPlaceholderCharacter('_');
        dff = new DefaultFormatterFactory(mask);
        textField.setFormatterFactory(dff);
        return mask;
    }
}
