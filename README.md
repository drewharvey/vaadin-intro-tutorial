# Acme CRM — Vaadin Tutorial Starter

The starter project for the **Vaadin Introduction Tutorial**. A Spring Boot + Vaadin app where you build your UI in pure Java — no HTML, no JavaScript.

This is the **starting point**: it ships the application shell (app name + navigation) and a pre-baked `Customer` backend (a Spring Data JPA repository, service, and seed data in `src/main/resources/data.sql`) that stays dormant until the database chapter. The Customers view starts empty — you build the grid, forms, and full CRUD UI as you follow along.

---

## Run application

From the project folder:

```bash
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

No system Maven required — the wrapper is included. Then open **http://localhost:8080**.

The first start takes ~30 seconds while Maven downloads dependencies. You'll get the running app shell — the **Acme CRM** header and a **Customers** navigation entry — with an empty Customers page. When you see that, you're running and ready to start the tutorial.

> **Port 8080 already in use?** Stop the other process, or set `server.port=8081` in `src/main/resources/application.properties` and open that port instead.
>
> **To stop the app:** press `Ctrl+C` in the terminal (or the red Stop button if you launched from your IDE).

## Enable hotswap (live reload)

Running with `spring-boot:run` works, but Java code changes need a restart. For **live reload** — edit Java, see it in the browser without restarting — install the **Vaadin plugin** and start the app through it:

- **IntelliJ IDEA:** install *Vaadin* from the JetBrains Marketplace → **Debug using Hotswap Agent** (dropdown next to Run). *Just installed it? Let IntelliJ finish indexing, or restart it, if the menu item isn't there yet.*
- **VS Code:** install the *Vaadin* extension → **Vaadin: Debug using Hotswap Agent** from the command palette.
- **Eclipse:** install the *Vaadin* plugin → right-click the project → **Run As → Vaadin Application**.

This is what makes the edit-and-see-it loop feel instant — and it's required for the AI edits in [Vaadin Copilot](https://vaadin.com/docs/latest/tools/copilot).

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
