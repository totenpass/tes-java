tes-java
=======

Java implementation of the Total Encryption Standard [specifications](https://github.com/totenpass/tes-specs)

# How to use tes-java

You can integrate this java library into your project *or* use the command line interface ([CLI](https://github.com/totenpass/tes-java/blob/main/src/main/java/com/totenpass/tes/CLI.java)) to decrypt your data from the console.

## Examples

Have a look at the [CLI.java](https://github.com/totenpass/tes-java/blob/main/src/main/java/com/totenpass/tes/CLI.java) and [TestVectors.java](https://github.com/totenpass/tes-java/blob/main/src/test/java/com/totenpass/tes/TestVectors.java) source code to see examples on how to call this library.

In most cases you can use a single function call to decrypt the QRCode content:

```java
import com.totenpass.tes.TES;

String content;
char[] passphrase;

Plaintext pt = TES.decryptAndDecode(content, passphrase);

// do something with `pt`
```

# Contributing

Every contribution is welcome!

If you feel that something can be improved or should be fixed, feel free to open an issue with the feature or the bug found.

If you want to fork and open a pull request (adding features or fixes), feel free to do it. Create a new branch from the main branch and open your PR.