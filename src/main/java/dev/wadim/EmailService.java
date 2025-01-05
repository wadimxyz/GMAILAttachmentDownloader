package dev.wadim;

import jakarta.mail.*;
import jakarta.mail.search.SearchTerm;
import jakarta.mail.search.SubjectTerm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class EmailService {

    private final Properties config;

    public EmailService(Properties config) {
        this.config = config;
    }

    public void downloadAttachments(String subjectKeyword) {
        try {
            Session session = Session.getDefaultInstance(config);
            Store store = session.getStore();
            store.connect(config.getProperty("gmail.username"), config.getProperty("gmail.app.password"));

            String folderName = config.getProperty("mail.folder", "INBOX").trim();
            Folder inbox = store.getFolder(folderName);
            inbox.open(Folder.READ_ONLY);

            SearchTerm searchTerm = new SubjectTerm(subjectKeyword);
            Message[] messages = inbox.search(searchTerm);

            for (Message message : messages) {
                saveAttachmentsFromMessage(message);
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAttachmentsFromMessage(Message message) {
        try {
            if (message.isMimeType("multipart/*")) {
                Multipart multipart = (Multipart) message.getContent();
                saveNestedParts(multipart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveNestedParts(Multipart multipart) {
        try {
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("multipart/*")) {
                    saveNestedParts((Multipart) bodyPart.getContent());
                } else if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                    saveAttachment(bodyPart);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAttachment(BodyPart bodyPart) {
        try {
            String downloadFolder = config.getProperty("download.folder");
            File dir = new File(downloadFolder);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName = bodyPart.getFileName();
            File file = new File(dir, fileName);

            try (InputStream is = bodyPart.getInputStream();
                 FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buf = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buf)) != -1) {
                    fos.write(buf, 0, bytesRead);
                }
            }
            System.out.println("Attachment saved: " + file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 