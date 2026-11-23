# Callback Pattern — File Upload Progress

## Intent

Pass a piece of code (the **callback**) to another object, so that object can **call it back** while doing its work — here, to report upload progress.

This example is **synchronous**: the callback runs on the same thread, during `upload(...)`. (See `../filedownload/` for an asynchronous callback.)

## Problem

Without a callback, the uploader has to decide how progress is shown:

```java
void upload(String file) {
    ... // send chunk
    System.out.println(percent + "%");   // what if the UI wants a progress bar?
}
```

## Solution

The uploader only calls `callback.onProgress(file, percent)`. Since `ProgressCallback` has one method, the client can pass a lambda or a method reference:

```java
uploader.upload("photo.jpg", (file, percent) -> System.out.println(file + ": " + percent + "%"));
uploader.upload("notes.txt", ZMain::notifyWhenDone);
```

## Structure

```
Client --upload(file, callback)--> FileUploader
                                       |
                    for each chunk:    v
                    callback.onProgress(file, percent)
                                       |
             lambda / method reference (decided by the client)
```

## Participants

| Class | Role |
|-------|------|
| `ProgressCallback` | Functional callback interface |
| `FileUploader` | Does the work and calls the callback after each chunk |
| `ZMain` | Client: passes lambdas / a method reference as callbacks |

## Run

```bash
java -cp target/classes other.callback.fileupload.ZMain
```

## Real-World Examples

`List.forEach(Consumer)`, `Comparator` passed to `List.sort`, `Files.walkFileTree(path, FileVisitor)`, progress listeners in HTTP clients.
