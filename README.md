## About

This is the third iteration of the small library helper I wrote. It is intended for my personal use, though I doubt it will actually be used. We are just way too unorganized :)

## Features

This is the frontend app for the manager.
Its features are therefore the same as for the backend,
but it serves as a thing client and knows basically nothing.

It allows:
* Adding books by ISBN (either input manually or scanned from a barcode)
* Deleting books by ISBN (either input manually or scanned from a barcode)
* Deleting books via the query result page
* Creating, Deleting and Editing book locations
* Assigning book locations to books when creating them or afterwards
* Querying the server for books, listing them, showing detail pages and allowing edits

## Libraries

The barcode scanning is provided by Zebra crossing

Network requests are made with `Fuel`,
a kotlin friendly HTTP library, though I needed a thin wrapping layer on top.

JSON (de-)serialization is done by Gson, although it is quite big.

Dependency injecting on android involves a lot more than on the backend,
but it is still handled by `Dagger 2`.

The android app, as well as the server, make use of Kotlin coroutines to manage async requests.
This is quite visible in the presenters of the app,
as they essentially all just delegate to a web request.

Picasso is responsible for downloading, caching and displaying images.

## Video
[Here. Gifs are so constraining.](https://streamable.com/tshqj)
