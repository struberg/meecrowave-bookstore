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

import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
 * Die REST Resource selbst.
 *
 * @author <a href="mailto:struberg@apache.org">Mark Struberg</a>
 */
@ApplicationScoped
@Path("/book")
public class BookResource {

    /**
     * Wir injecten uns das backend als CDI bean.
     * Damit koennen wir die ganzen JAX-RS spezifischen Details
     * hier erledigen und die Datenhaltung in eigene Klassen auslagern.
     */
    private @Inject BookstoreService bookstoreService;

    /**
     * Mittels GET koennen wir auf ein vorab gespeichertes Buch zugreifen
     *
     * Mittels JSON-B wird das retournierte Objekt aufgrund der Produces
     * Annotation direkt in JSON umgewandelt!
     *
     * ACHTUNG: Es gibt in JavaEE 2 verschiedene Produces annotations.
     * Neben der hier verwendeten javax.ws.rs.Produces gibt es in CDI auch noch
     * javax.enterprise.inject.Produces zum Kennzeichnen von Producer Methoden!
     *
     * Beispielaufruf:
     * <pre>
     * $>curl http://localhost:8080/book/0345391802
     * </pre>
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{isbn}")
    public Book getByIsbn(@PathParam("isbn") String isbn) {
        Objects.nonNull(isbn);
        return bookstoreService.getBookByIsbn(isbn);
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
    public void storeBook(Book newBook) {
        bookstoreService.storeBook(newBook);
    }

}
