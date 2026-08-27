# Vaadin Tutorial Starter — Polaris CRM

**This is the starting point.** Clone it, run it, and build **Polaris CRM** alongside the video:

### ▶ [Build a Full-Stack App in Pure Java — Vaadin Flow Tutorial](https://youtu.be/lTrcM0iP7O4)

A Spring Boot + Vaadin app where you build your UI in pure Java — no HTML, no JavaScript.

> **Looking for the finished code?** It's on the [`tutorial-complete`](../../tree/tutorial-complete)
> branch, with a tag at every section of the video so you can jump to any checkpoint.

---

## What's already here

The boring parts are done so the video can stay on the UI. The data layer is written and
waiting — you ignore it until the backend section (43:52).

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── Application.java              Spring Boot entry point
│   │   ├── ui/
│   │   │   └── HomeView.java             the empty first view — you start typing here
│   │   └── backend/                      pre-baked data layer (ignored until 43:52)
│   │       ├── Customer.java             entity
│   │       ├── CustomerRepository.java   data access
│   │       ├── CustomerService.java      backend service
│   │       └── Status.java
│   └── resources/
│       ├── META-INF/resources/styles.css empty — your custom CSS goes here (53:57)
│       ├── application.properties
│       └── data.sql                      sample customers
└── test/java/com/example/
    ├── backend/
    │   └── CustomerServiceTest.java
    └── ui/
        └── HomeViewTest.java
```

`HomeView` is deliberately empty — an `@Route("")` class with an empty constructor. That empty
constructor is where the tutorial's first line of code goes.

---

## Run application

From the project folder:

```bash
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

No system Maven required — the wrapper is included. Then open **http://localhost:8080**.

The first start takes ~30 seconds while Maven downloads dependencies. You'll get the running app —
a blank page for now, since the **Polaris CRM** UI is what you build in the tutorial. When the
blank page loads without errors, you're running and ready to start.

> **Port 8080 already in use?** Stop the other process, or set `server.port=8081` in
> `src/main/resources/application.properties` and open that port instead.
>
> **To stop the app:** press `Ctrl+C` in the terminal (or the red Stop button if you launched
> from your IDE).

## Enable hotswap (live reload)

Running with `spring-boot:run` works, but Java code changes need a restart. For **live reload** —
edit Java, see it in the browser without restarting — install the **Vaadin plugin** and start the
app through it:

- **IntelliJ IDEA:** install *Vaadin* from the JetBrains Marketplace → **Debug using Hotswap
  Agent** (dropdown next to Run). *Just installed it? Let IntelliJ finish indexing, or restart it,
  if the menu item isn't there yet.*
- **VS Code:** install the *Vaadin* extension → **Vaadin: Debug using Hotswap Agent** from the
  command palette.
- **Eclipse:** install the *Vaadin* plugin → right-click the project → **Run As → Vaadin
  Application**.

This is what makes the edit-and-see-it loop feel instant — and it's required for the AI edits in
[Vaadin Copilot](https://vaadin.com/docs/latest/tools/copilot). It's covered at
[01:20](https://youtu.be/lTrcM0iP7O4?t=80) in the video.

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
