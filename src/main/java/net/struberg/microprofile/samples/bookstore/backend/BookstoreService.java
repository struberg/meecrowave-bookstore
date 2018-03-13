/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.struberg.microprofile.samples.bookstore.backend;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dieses Service wuerde in echt die Daten natuerlich irgendwo hin persistieren.
 * Entweder in eine Datenbank, ein NoSQL store wie Cassandra, etc
 *
 * Um vorerst das Sample einfach zu gestalten speichern wir die Daten
 * Allerdings nur in einer HashMap
 *
 * @author <a href="mailto:struberg@apache.org">Mark Struberg</a>
 */
@ApplicationScoped
public class BookstoreService {
    /**
     * Wird in Meecrowave automatisch auf log4j2 umgeleitet.
     * In anderen MicroProfile servern funktioniert es natürlich genauso
     */
    private static final Logger logger = LogManager.getLogger(BookstoreService.class.getName());

    private ConcurrentMap<String, Book> booksByIsbn = new ConcurrentHashMap<>();

    /**
     * Und wir tueten gleich ein paar gute Buecher ein...
     */
    @PostConstruct
    private void init() {
        storeBook(new Book("3538069727", "Jules Verne", "Reise zum Mittelpunkt der Erde"));
        storeBook(new Book("3868200819", "Arthur Schopenhauer", "Aphorismen zur Lebensweisheit"));
        storeBook(new Book("3100921046", "Roger Willemsen", "Die Enden der Welt"));
        storeBook(new Book("0345391802", "Douglas Adams", "The Hitchhiker's Guide to the Galaxy"));
    }

    public Book getBookByIsbn(String isbn) {
        return booksByIsbn.get(isbn);
    }

    public Book storeBook(Book book) {
        logger.info("storing book {}", book);
        return booksByIsbn.put(book.getIsbn(), book);
    }
}
