package com.example;

import com.example.spi.SpiLoader;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Unit test for simple App.
 */
public class AppTest{
    @SneakyThrows
    public static void main(String[] args) {


                // create client using endpoints
                Client client = Client.builder().endpoints("http://localhost:2379")
                        .build();

                KV kvClient = client.getKVClient();
                ByteSequence key = ByteSequence.from("test_key".getBytes());
                ByteSequence value = ByteSequence.from("test_value".getBytes());

                // put the key-value
                kvClient.put(key, value).get();

                // get the CompletableFuture
                CompletableFuture<GetResponse> getFuture = kvClient.get(key);

                // get the value from CompletableFuture
                GetResponse response = getFuture.get();

                // delete the key
                kvClient.delete(key).get();
            }



}

