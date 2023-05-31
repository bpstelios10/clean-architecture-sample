# SPRING WEB WITH CLEAN ARCHITECTURE

COMMIT BY COMMIT IMPLEMENTATION OF A SPRING WEB APP THAT IMPLEMENTS CLEAN ARCHITECTURE

The main focus is to separate business entity, use cases and infrastructure (gateways, controllers, repositories etc)
into 3 different levels.
In our case the 3 levels will be the 3 different packages: domain, usecases, adapter.

After we separate the 3 layers, we need to use interfaces for any interactions between layers.
Also, keep dependencies pointing from outer layer to inner and avoid having dependencies between layers that are not
next to each other (usually an exception is a repository which will point to business entities).

So our example should look like this:

<img src="docs-resources/clean-architecture.png" style="margin: auto; display: block;" width="800" height="400" alt="class diagram">

Notes: for technical documentation/guides have a look [here](docs-resources/TECH-DOC.md)
