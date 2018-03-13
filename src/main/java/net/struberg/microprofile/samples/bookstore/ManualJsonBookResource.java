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
package net.struberg.microprofile.samples.bookstore;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.struberg.microprofile.samples.bookstore.backend.Book;
import net.struberg.microprofile.samples.bookstore.backend.BookstoreService;

/**
 * In dieser Klasse stelle ich ein paar alternative Ansaetze zur Behandlung von JSON etc vor.
 * Fuer die optimale Implementierung siehe {@link BookResource}.
 *
 * Diese Vorgangsweise ist bei jenen Servern notwendig die kein JSONB nativ unterstuetzen.
 *
 * Wer diese Implementierung mit jener direkt in {@link BookResource} vergleicht, der
 * wird schnell verstehen, warum JSONB so viel angenehmer zu verwenden ist.
 *
 * @author <a href="mailto:struberg@apache.org">Mark Struberg</a>
 */
@ApplicationScoped
@Path("/otherbook")
public class ManualJsonBookResource {

    /**
     * Wir injecten uns das backend als CDI bean.
     * Damit koennen wir die ganzen JAX-RS spezifischen Details
     * hier erledigen und die Datenhaltung in eigene Klassen auslagern.
     */
    private @Inject BookstoreService bookstoreService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/manual/{isbn}")
    public String getByIsbn(@PathParam("isbn") String isbn) {
        Objects.nonNull(isbn);
        Book book = bookstoreService.getBookByIsbn(isbn);
        if (book == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();

        // erzeuge einen JsonGenerator und konstruiere das
        // benoetigte JSON manuell.
        Json.createGenerator(stringWriter)
                .writeStartObject()
                .write("isbn", book.getIsbn())
                .write("author", book.getAuthor())
                .write("title", book.getTitle())
                .writeEnd();

        return stringWriter.toString();
    }

    /**
     * Wir verwenden PUT um das per JSON uebergebene Buch
     * am Server zu speichern.
     *
     * Beispielaufruf:
     * <pre>
     * $>curl -H "Content-Type: application/json" -X PUT -d '{"isbn":"0141439742","author":"Charles Dickens","title":"Oliver Twist"}' http://localhost:8080/book
     * </pre>
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void storeBook(String newBookJson) {

        // erzeuge ein JsonObject mittels eines JsonReader
        JsonObject bookJsonObject = Json.createReader(new StringReader(newBookJson))
                .readObject();

        // und befuelle das neue Buch manuell.
        Book newBook = new Book();
        newBook.setIsbn(bookJsonObject.getString("isbn"));
        newBook.setAuthor(bookJsonObject.getString("author"));
        newBook.setTitle(bookJsonObject.getString("title"));

        bookstoreService.storeBook(newBook);
    }


}
