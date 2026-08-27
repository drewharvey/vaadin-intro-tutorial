# Polaris CRM — Finished Tutorial App

Build a modern Java web app UI in Java using the [Vaadin Flow framework](https://vaadin.com) - no JavaScript or HTML.

This branch holds the finished app, built one commit at a time. The tutorial comes in both video and a text versions:
 * [Video tutorial](https://youtu.be/lTrcM0iP7O4)
 * [Text tutorial](https://drewharvey.github.io/vaadin-intro-tutorial/)

> **Want to build it yourself?** Start on the [`main`](../../tree/main) branch — that's the
> starter project the tutorial begins from.

---

## Video checkpoints

Every section of the video has a tag marking the code as it stood when that section ended:

```bash
git checkout 05-binder-validation
```

| Tag | Video section | |
|---|---|---|
| `01-your-first-view` | Your first view, components and layouts | [02:28](https://youtu.be/lTrcM0iP7O4?t=148) |
| `02-data-in-a-grid` | Displaying data in a Grid | [05:57](https://youtu.be/lTrcM0iP7O4?t=357) |
| `03-filtering-with-signals` | Filtering with Signals | [10:34](https://youtu.be/lTrcM0iP7O4?t=634) |
| `04-details-form` | A details form bound to grid selection | [18:06](https://youtu.be/lTrcM0iP7O4?t=1086) |
| `05-binder-validation` | Editing with Binder and validation | [24:26](https://youtu.be/lTrcM0iP7O4?t=1466) |
| `06-buffered-save-discard` | Save and discard with buffered mode | [30:03](https://youtu.be/lTrcM0iP7O4?t=1803) |
| `07-toolbar-and-adding` | A toolbar and adding customers | [35:20](https://youtu.be/lTrcM0iP7O4?t=2120) |
| `08-delete-confirm-dialog` | Deleting with a confirm dialog | [39:32](https://youtu.be/lTrcM0iP7O4?t=2372) |
| `09-real-backend` | Connecting the real backend | [43:52](https://youtu.be/lTrcM0iP7O4?t=2632) |
| `10-app-shell-navigation` | App shell and navigation | [48:18](https://youtu.be/lTrcM0iP7O4?t=2898) |
| `11-theme-variants` | Styling with theme variants | [51:42](https://youtu.be/lTrcM0iP7O4?t=3102) |
| `12-custom-css-themes` | Custom CSS and themes | [53:57](https://youtu.be/lTrcM0iP7O4?t=3237) |
| `13-theme-generator` | Branding with the theme generator | [58:13](https://youtu.be/lTrcM0iP7O4?t=3493) |
| `final` | The finished app | |

`git checkout tutorial-complete` returns you to the finished state. The commits between tags are
the individual edits, if you want a finer-grained diff.

---

## Project structure

Two routes: **`/`** is the components and layouts scene from the early sections, and
**`/customers`** is Polaris CRM. `MainLayout` wraps both.

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── Application.java              Spring Boot entry point
│   │   ├── ui/
│   │   │   ├── MainLayout.java           app shell — drawer, logo, SideNav
│   │   │   ├── HomeView.java             the components and layouts scene
│   │   │   └── CustomerListView.java     the CRM itself
│   │   └── backend/                      pre-baked backend
│   │       ├── Customer.java             entity
│   │       ├── CustomerRepository.java   data access
│   │       ├── CustomerService.java      backend service
│   │       └── Status.java
│   └── resources/
│       ├── META-INF/resources/styles.css the custom theme
│       ├── application.properties
│       ├── vaadin-featureflags.properties
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

No system Maven required — the wrapper is included. Then open **http://localhost:8080** and use
the drawer to reach **Customers**.

> **Port 8080 already in use?** Stop the other process, or set `server.port=8081` in
> `src/main/resources/application.properties` and open that port instead.
>
> **To stop the app:** press `Ctrl+C` in the terminal (or the red Stop button if you launched
> from your IDE).

---

## Build for production

```bash
./mvnw package
java -jar target/*.jar
```

## Learn more

- [Signals](https://vaadin.com/docs/latest/flow/ui-state) — the reactive state used throughout this app
- [Components](https://vaadin.com/docs/latest/components) — 50+ UI components, all callable from Java
- [Vaadin Copilot](https://vaadin.com/docs/latest/tools/copilot) — visual + AI editing in the browser
- [Full documentation](https://vaadin.com/docs)
