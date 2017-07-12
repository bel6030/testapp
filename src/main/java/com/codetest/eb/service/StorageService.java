package com.codetest.eb.service;

import java.io.InputStream;
import java.util.UUID;

public interface StorageService {

    void store(InputStream is, UUID uuid);

    InputStream retrieve(UUID uuid);
}
