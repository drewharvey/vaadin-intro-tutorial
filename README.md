# Polaris CRM — the finished tutorial app

**This is the completed project.** It's the app built end-to-end in:

### ▶ [Build a Full-Stack App in Pure Java — Vaadin Flow Tutorial](https://youtu.be/lTrcM0iP7O4)

A Spring Boot + Vaadin CRM — sortable grid, signal-driven filtering, a validated form with
buffered save/discard, add and delete with a confirm dialog, a real database backend, an app
shell with navigation, and custom theming. All in pure Java — no HTML, no JavaScript.

> **Want to build it yourself?** Start on the [`main`](../../tree/main) branch — that's the
> starter project the video begins from. This branch is the answer key.

---

## Video checkpoints

Every section of the video has a tag marking the code **as it stood when that section ended**.
Check one out to see exactly what was on screen:

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

## What's in the finished app

```
src/
├── main/
│   ├── java/com/example/
│   │   ├── Application.java              entry point + @StyleSheet, @ColorScheme, @Push
│   │   ├── ui/
│   │   │   ├── MainLayout.java           @Layout app shell — drawer, logo, SideNav
│   │   │   ├── HomeView.java             @Route("") — the components & layouts scene
│   │   │   └── CustomerListView.java     @Route("customers") — the CRM itself
│   │   └── backend/
│   │       ├── Customer.java             entity
│   │       ├── CustomerRepository.java   data access
│   │       ├── CustomerService.java      backend service
│   │       └── Status.java
│   └── resources/
│       ├── META-INF/resources/styles.css the custom theme
│       ├── application.properties
│       ├── vaadin-featureflags.properties
│       └── data.sql                      sample customers
└── test/java/com/example/
    ├── backend/
    │   └── CustomerServiceTest.java
    └── ui/
        └── HomeViewTest.java
```

Two routes: **`/`** is the components-and-layouts scene from the early sections, and
**`/customers`** is Polaris CRM. `MainLayout` wraps both.

---

## Run application

From the project folder:

```bash
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
```

No system Maven required — the wrapper is included. Then open **http://localhost:8080** and use
the drawer to reach **Customers**.

The first start takes ~30 seconds while Maven downloads dependencies.

> **Port 8080 already in use?** Stop the other process, or set `server.port=8081` in
> `src/main/resources/application.properties` and open that port instead.
>
> **To stop the app:** press `Ctrl+C` in the terminal (or the red Stop button if you launched
> from your IDE).

## Enable hotswap (live reload)

To edit Java and see it in the browser without restarting, install the **Vaadin plugin** and
start the app through it:

- **IntelliJ IDEA:** install *Vaadin* from the JetBrains Marketplace → **Debug using Hotswap
  Agent** (dropdown next to Run).
- **VS Code:** install the *Vaadin* extension → **Vaadin: Debug using Hotswap Agent** from the
  command palette.
- **Eclipse:** install the *Vaadin* plugin → right-click the project → **Run As → Vaadin
  Application**.

Required for the AI edits in [Vaadin Copilot](https://vaadin.com/docs/latest/tools/copilot).

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
