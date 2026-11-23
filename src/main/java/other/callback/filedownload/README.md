# Callback Pattern

## Intent

Pass a piece of code (the **callback**) to another object, so that object can **call it back** when its work is done — without knowing what the callback does.

## Problem

Without a callback, the downloader has to know what happens after a download:

```java
void download(String url) {
    ... // download
    pdfViewer.open(file);   // downloader is now tied to the viewer
}
```

Or the client blocks and waits for the result, doing nothing in the meantime.

## Solution

The client hands a `DownloadCallback` to the downloader and continues. When the download finishes, the downloader calls `onSuccess` or `onFailure`:

```java
downloader.download("https://example.com/report.pdf", new ConsoleDownloadCallback());
System.out.println("not waiting, doing other work...");
```

Each client decides what happens with the result by passing a different callback.

## Structure

```
Client --download(url, callback)--> FileDownloader
  |                                      |
  | continues its own work               | downloads in background
  |                                      v
  |                     callback.onSuccess(...) / onFailure(...)
  v
DownloadCallback <-- ConsoleDownloadCallback / anonymous class
```

## Participants

| Class | Role |
|-------|------|
| `DownloadCallback` | Callback interface: what to run when the work ends |
| `ConsoleDownloadCallback` | Concrete callback |
| `FileDownloader` | Does the work, then invokes the callback |
| `ZMain` | Client: chooses the callback and passes it in |

## Run

```bash
java -cp target/classes other.callback.filedownload.ZMain
```

## Real-World Examples

`CompletableFuture.thenAccept`, `Runnable` passed to `Thread`, Swing `ActionListener`, JavaScript `setTimeout` / Node.js `fs.readFile(path, cb)`.
