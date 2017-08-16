package com.shc.scinventory.enterpriseShippingToolJobs.Utilities;

import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class FileReader {
    public static String readResource(final String fileName) throws IOException {
        return Resources.toString(Resources.getResource(fileName), Charsets.UTF_8);
    }
}
