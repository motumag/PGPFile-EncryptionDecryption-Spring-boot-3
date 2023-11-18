package com.example.perfectpgpfileencdec;
import com.example.perfectpgpfileencdec.services.KeyBasedFileProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class PgpEncryptionDecryptionApplication implements CommandLineRunner {
    private Logger logger= LoggerFactory.getLogger(PgpEncryptionDecryptionApplication.class);
    @Value("classpath:output/sample.pgp")
    private Resource outPutResourse;
    @Value("classpath:files/sample.txt")
    private Resource inPutResourse;
    @Value("classpath:files/sample.pgp")
    private Resource inPutResoursePGP;

    @Value("classpath:keys/motuma-pub.asc")
    private Resource motumaPublicKeys;
    @Value("classpath:keys/motuma-priv.asc")
    private Resource motumaPrivateKeys;
    public static void main(String[] args) {
        SpringApplication.run(PgpEncryptionDecryptionApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        String outPutFileName=outPutResourse.getFile().toPath().toString();
        String inputFileName=inPutResourse.getFile().toPath().toString();

        String inPutResoursePGPDecrption=inPutResoursePGP.getFile().toPath().toString();
        String publicKeyFileName=motumaPublicKeys.getFile().getPath().toString();
        String privateKeyFileName=motumaPrivateKeys.getFile().getPath().toString();

        logger.info("Encrypting {}", inputFileName);
        KeyBasedFileProcessor.encryptFile(outPutFileName, inputFileName, publicKeyFileName, true, true);
        logger.info("Successfully Encrypted {}", inputFileName);

        logger.info("Decrypting {}", inPutResoursePGPDecrption);
        KeyBasedFileProcessor.decryptFile(inPutResoursePGPDecrption, privateKeyFileName, "motumag".toCharArray(), null);
        logger.info("Successfully Decrypted {}", inPutResoursePGPDecrption);

    }
}

