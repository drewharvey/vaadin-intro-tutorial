# Vaadin Tutorial Starter — Polaris CRM

Build a modern Java web app UI in Java using the [Vaadin Flow framework](https://vaadin.com) - no JavaScript or HTML.

This is the starter project used to code alongside the tutorial. The tutorial comes in both video and a text versions:
 * [Video tutorial](https://youtu.be/lTrcM0iP7O4)
 * [Text tutorial](https://drewharvey.github.io/vaadin-intro-tutorial/)

> **Looking for the finished code?** It's on the [`tutorial-complete`](../../tree/tutorial-complete)
> branch, with a tag at every section of the video so you can jump to any checkpoint.

---

## Starter project structure

The starter comes with a prebuilt Spring Boot backend. The UI will be written from scratch.

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── Application.java              Spring Boot entry point
│   │   ├── ui/
│   │   │   └── HomeView.java             the empty first view — you start typing here
│   │   └── backend/                      pre-baked backend
│   │       ├── Customer.java             entity
│   │       ├── CustomerRepository.java   data access
│   │       ├── CustomerService.java      backend service
│   │       └── Status.java
│   └── resources/
│       ├── META-INF/resources/styles.css empty — your custom CSS goes here
│       ├── application.properties
│       └── data.sql                      sample customers
└── test/java/com/example/                automated tests
```

---

## Run the application with hotswap (live reload) - RECOMMENDED

Running the app this way allows us to make code changes and see them instantly in the browser. This speeds up development time considerably, especially when making tweaks to the UI.

> Hotswap is also required to use Vaadin Copilot.

Instructions:

- [Video instructions](https://youtu.be/QdRV75GADxk?t=311)
- **IntelliJ IDEA:** install *Vaadin* plugin from the JetBrains Marketplace → **Debug using Hotswap
  Agent** (dropdown next to Run). *Just installed it? Let IntelliJ finish indexing, or restart it,
  if the menu item isn't there yet.*
- **VS Code:** install the *Vaadin* extension → **Vaadin: Debug using Hotswap Agent** from the
  command palette.
- **Eclipse:** see [instructions in Vaadin docs](https://vaadin.com/docs/latest/getting-started/dev-environment/run/eclipse#enabling-hotswap).

## Run application without hotswap

Running the application this way will require server restarts for some changes.

### From the IDE

Use the IDE's run command on the Application.java class. Most IDE's will automatically detect Application.java as the entry point, but if they do not, you can right-click the class and select Run.

### From the terminal (command line)

From the project folder:

```bash
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

No system Maven required — the wrapper is included. Then open **http://localhost:8080**.

> **Port 8080 already in use?** Stop the other process, or set `server.port=8081` in
> `src/main/resources/application.properties` and open that port instead.
>
> **To stop the app:** press `Ctrl+C` in the terminal (or the red Stop button if you launched
> from your IDE).

---

## Stuck? Compare against the finished code

Every section of the video has a tag on the [`tutorial-complete`](../../tree/tutorial-complete)
branch. To see the code as it stood at the end of any section:

```bash
git fetch origin tutorial-complete
git checkout 09-real-backend      # or any tag from the list below
```

`git checkout main` puts you back on your own work. Run `git tag` for the full list, or see the
[table on the `tutorial-complete` branch](../../tree/tutorial-complete#video-checkpoints).

---

## Build for production

```bash
./mvnw package
java -jar target/*.jar
```

## Learn more

- [Vaadin Quickstart](https://vaadin.com/quickstart) — the 5-minute getting-started path
- [Components](https://vaadin.com/docs/latest/components) — 50+ UI components, all callable from Java
- [Vaadin Copilot](https://vaadin.com/docs/latest/tools/copilot) — visual + AI editing in the browser
- [Full documentation](https://vaadin.com/docs)
