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

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Diese Klasse benoetigt man eigentlich nur wenn man nicht auf einem
 * Servlet Container sondern 'standalone' laufen will.
 *
 * In einem Servlet Container wird "/" als default path sowieso als gegeben angenommen.
 * Fuer MeecroWave, TomEE, Payara, Wildfly, etc brauchen wir also diese Klasse streng
 * genommen nicht mal schreiben. Darum wurde sie auch im Artikel nicht erwaehnt.
 *
 * @author <a href="mailto:struberg@apache.org">Mark Struberg</a>
 */
@ApplicationScoped
@ApplicationPath("/")
public class BookStoreApplication extends Application {
}
