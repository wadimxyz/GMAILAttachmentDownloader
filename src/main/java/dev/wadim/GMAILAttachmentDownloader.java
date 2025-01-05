package dev.wadim;

import java.util.Properties;

public class GMAILAttachmentDownloader {

    public static void main(String[] args) {
        Properties config = ConfigLoader.loadConfig();
        EmailService emailService = new EmailService(config);

        String subjectKeyword = config.getProperty("subject.keyword");
        if (args.length > 0) {
            subjectKeyword = args[0];
        }

        emailService.downloadAttachments(subjectKeyword);
    }
}
