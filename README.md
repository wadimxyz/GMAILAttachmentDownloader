# IMAP Attachment Downloader

A Java program to download email attachments based on a specific subject from gmail.

## Prerequisites

- Java 17 or higher
- Maven

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/wadimxyz/GMAILAttachmentDownloader.git
   cd GMAILAttachmentDownloader
   ```

2. Configure your email settings in `src/main/resources/config.properties`.

3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

## Configuration

Edit the `src/main/resources/config.properties` file to set your email configuration and other settings.

### Configuration Properties

- `mail.imaps.host`: The IMAP server host (e.g., `imap.gmail.com`).
- `mail.imaps.port`: The IMAP server port (e.g., `993`).
- `mail.imaps.ssl.enable`: Enable SSL (e.g., `true`).
- `mail.store.protocol`: The protocol to use (e.g., `imaps`).
- `gmail.username`: Your email address.
- `gmail.app.password`: Your app-specific password.
- `download.folder`: The folder where attachments will be saved.
- `subject.keyword`: The keyword to search for in email subjects.
- `mail.folder`: The email folder to search in (e.g., `INBOX`).

## Usage

Run the program:
```bash
java -jar target/IMAPAttachmentDownloader-1.0-SNAPSHOT.jar ["subject keyword"]
```

If you don't provide a subject keyword, it will use the one specified in the config.properties file.
