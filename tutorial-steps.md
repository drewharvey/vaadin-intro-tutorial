# Tutorial steps: from starter to Polaris CRM

How the code gets from the **`main` branch** (starter: empty `HomeView`, dormant `backend` package)
to the **`tutorial-complete` branch** (the finished app), chapter by chapter, following
`2027-06-vaadin-intro-tutorial.md`.

Each step shows the code written or changed in that chapter — block-level changes and the
significant parts, not every line. **First-time concepts are called out at the top of each step.**
The final state of every file matches `tutorial-complete` exactly; the intermediate snapshots are
reconstructions that follow the outline's teaching decisions (manual tedium before Binder, text
button before icon, etc.).

Chapters 1–2 (intro, "What is Vaadin?") and 15 (wrap-up) have no code and are omitted here apart
from their concept notes.

---

## Step 0 — Chapter 3: Project setup

*No code written. Concepts: none (Vaadin Copilot/dev-mode indicator acknowledged in one sentence — "part of dev mode, ignore it for this tutorial").*

Download the starter, open it in an IDE, run it:

```bash
./mvnw spring-boot:run
```

Open http://localhost:8080 — a blank page. The starter ships:

```
src/main/java/com/example/
├── Application.java     Spring Boot entry point — don't touch
├── ui/
│   └── HomeView.java    empty first view — the tutorial starts here
└── backend/             pre-baked data layer — ignored until chapter 12
```

---

## Step 1 — Chapter 4: Your first view

**Concept introduced: C2 — views & `@Route`.** A view is a plain Java class; `@Route` maps a URL
to it. This is "where does my code go."

Look at the starter's `HomeView` — this is the whole file:

```java
@Route("")
class HomeView extends VerticalLayout {

    HomeView() {
    }
}
```

`@Route("")` maps it to the root URL. To prove the code→browser connection, add one line to the
constructor and watch the page change:

```java
HomeView() {
    add(new Paragraph("Welcome to Polaris CRM"));
}
```

*(This line is temporary — chapter 5 replaces it. The page is bare on purpose: the frame around
it — the app shell — is built in chapter 13.)*

---

## Step 2 — Chapter 5: Components & layouts

**Concept introduced: C3 — components are UI elements, layouts arrange them, `add()` composes,
and layouts nest** (a layout *is* a component).

Replace the paragraph with real components in `HomeView`. The two buttons go in a
`HorizontalLayout` *inside* the view's `VerticalLayout` — that's nesting, shown not told:

```java
HomeView() {
    var name = new TextField("Name");
    add(name);

    var sayHelloBtn = new Button("Say hello");
    var resetBtn = new Button("Reset");

    var buttonsLayout = new HorizontalLayout();
    buttonsLayout.add(sayHelloBtn, resetBtn);
    add(buttonsLayout);
}
```

On screen: a text field with two buttons side by side beneath it. They don't do anything yet.

---

## Step 3 — Chapter 6: Listeners — making it interactive

**Concepts introduced: C4 — listeners are *the* interaction pattern ("they all look like this"),
and C5 — field values via `getValue()`/`setValue()`.** Lambda syntax gets a brief explanation at
first use.

Wire the buttons up:

```java
var sayHelloBtn = new Button("Say hello");
sayHelloBtn.addClickListener(event -> {
    Notification.show("Hello " + name.getValue());
});

var resetBtn = new Button("Reset");
resetBtn.addClickListener(event -> {
    name.setValue("");
});
```

- **Say hello** *reads* the field (`getValue()`) and shows a `Notification` (Tier 3: "the feedback
  tool," one sentence).
- **Reset** *writes* the field (`setValue("")`).

The generalization moment: every Vaadin component reacts through listeners, and they all look
like this — `addSomethingListener(event -> ...)`.

`HomeView` is now finished; it stays exactly like this in the final app.

---

## Step 4 — Chapter 7: Display data in a Grid

**Concept introduced: C6 — Grid** (the ~3-lines credibility moment). **C2 revisited:** this is a
*new, second* view.

Create `CustomerListView` in the `ui` package. Until chapter 13 there's no navigation — you reach
it by typing `/customers` in the browser.

```java
@Route("customers")
class CustomerListView extends VerticalLayout {

    final Grid<Customer> grid = new Grid<>(Customer.class, false);

    CustomerListView() {
        grid.addColumn(Customer::getFirstName).setHeader("First name");
        grid.addColumn(Customer::getLastName).setHeader("Last name");
        grid.addColumn(Customer::getEmail).setHeader("Email");
        grid.addColumn(Customer::getStatus).setHeader("Status");
        grid.addColumn(Customer::getCustomerSince).setHeader("Customer since");
        grid.setItems(getSampleCustomers());

        grid.setSizeFull();
        setSizeFull();
        add(grid);
    }

    private List<Customer> getSampleCustomers() {
        return List.of(
                new Customer("Alice", "Nguyen", "alice.nguyen@meridian-labs.com",
                        Status.CUSTOMER, LocalDate.of(2023, 1, 15)),
                new Customer("Bob", "Martinez", "bob.martinez@bluefern.io",
                        Status.CUSTOMER, LocalDate.of(2022, 11, 3)),
                new Customer("Carol", "Schmidt", "carol.schmidt@meridian-labs.com",
                        Status.PROSPECT, null));
    }
}
```

Two acknowledgment beats, one sentence each:

- `Customer` comes from the `backend` package and carries JPA annotations — *"ignore those for
  now; they're how this becomes a database table — that's chapter 12."* Here it's used as a plain
  POJO in a hardcoded list.
- `Customer::getFirstName` is a method reference — shorthand for `customer -> customer.getFirstName()`.

---

## Step 5 — Chapter 8: Sorting & filtering

**No new concept — C4 revisited:** a second listener type (*value change*), now motivated by a
search field.

Sorting is one call per column:

```java
grid.addColumn(Customer::getFirstName)
        .setHeader("First name")
        .setSortable(true);
// ...same for the other columns
```

Add a search field above the grid and re-filter on every change. `ValueChangeMode.LAZY` means
"fire while typing, but only after a pause":

```java
final TextField filter = new TextField();

private HorizontalLayout createToolbar() {
    filter.setPlaceholder("Search...");
    filter.setClearButtonVisible(true);
    filter.setValueChangeMode(ValueChangeMode.LAZY);
    filter.addValueChangeListener(event -> updateList());

    return new HorizontalLayout(filter);
}
```

`updateList()` becomes the single place that decides what the grid shows. The search matches more
than just the name — modern search covers email and status too (a simple stream; one sentence on
what the chain does):

```java
private void updateList() {
    List<Customer> customers = getSampleCustomers();

    var query = filter.getValue();
    if (query != null && !query.isBlank()) {
        var lower = query.toLowerCase();
        customers = customers.stream()
                .filter(customer -> customer.getEmail().toLowerCase().contains(lower)
                        || customer.getFirstName().toLowerCase().contains(lower)
                        || customer.getLastName().toLowerCase().contains(lower)
                        || customer.getStatus().toString().toLowerCase().contains(lower))
                .toList();
    }

    grid.setItems(customers);
}
```

The constructor now does `add(createToolbar(), grid)` and calls `updateList()` instead of
`grid.setItems(...)`.

---

## Step 6 — Chapter 9: Show item details

**No new concept — C4 revisited again** (third listener type: *selection*) **and C5 revisited —
deliberately painfully.** This chapter builds the anti-pattern that Binder kills in chapter 10.

Add detail fields beside the grid. `FormLayout` is Tier 3: "a layout that arranges fields nicely."
`ComboBox` and `DatePicker` are just fields like `TextField` — same `setValue`/`getValue` contract:

```java
final TextField firstName = new TextField("First name");
final TextField lastName = new TextField("Last name");
final TextField email = new TextField("Email");
final ComboBox<Status> status = new ComboBox<>("Status");
final DatePicker customerSince = new DatePicker("Customer since");

// in the constructor:
status.setItems(Status.values());
var form = new FormLayout(firstName, lastName, email, status, customerSince);

var content = new HorizontalLayout(grid, form);
content.setFlexGrow(2, grid);
content.setFlexGrow(1, form);
content.setSizeFull();
add(createToolbar(), content);
```

Selecting a row populates the fields — **one `setValue()` per field, by hand**:

```java
grid.addSelectionListener(event -> {
    var customer = event.getFirstSelectedItem().orElse(null);
    if (customer != null) {
        firstName.setValue(customer.getFirstName());
        lastName.setValue(customer.getLastName());
        email.setValue(customer.getEmail());
        status.setValue(customer.getStatus());
        customerSince.setValue(customer.getCustomerSince());
    }
});
```

Say the pain out loud: five fields, five lines — and saving would mean five `getValue()` calls
back. There has to be a better way. (There is. Next chapter.)

---

## Step 7 — Chapter 10: Edit & save with Binder

**Concept introduced: C7 — Binder,** arriving as the relief for chapter 9's tedium.

A `Binder` connects the fields to the bean's getters/setters once; from then on it moves the data
both ways. Add validation while binding — required fields plus one real rule:

```java
private final Binder<Customer> binder = new Binder<>(Customer.class);

// in the constructor — replaces nothing yet, sits next to the fields:
binder.forField(firstName)
        .asRequired("First name is required")
        .bind(Customer::getFirstName, Customer::setFirstName);
binder.forField(lastName)
        .asRequired("Last name is required")
        .bind(Customer::getLastName, Customer::setLastName);
binder.forField(email)
        .asRequired("Email is required")
        .withValidator(new EmailValidator("Enter a valid email address"))
        .bind(Customer::getEmail, Customer::setEmail);
binder.forField(status)
        .asRequired("Status is required")
        .bind(Customer::getStatus, Customer::setStatus);
binder.forField(customerSince)
        .bind(Customer::getCustomerSince, Customer::setCustomerSince);
```

The five manual `setValue()` lines collapse into one:

```java
grid.addSelectionListener(event -> {
    var customer = event.getFirstSelectedItem().orElse(null);
    binder.readBean(customer);   // was: five setValue() calls
});
```

Save and Discard buttons show why *buffered* mode is worth having: `writeBean` copies the edits
into the bean only if validation passes; Discard is just reading the bean again.

```java
var save = new Button("Save");
save.addClickListener(event -> {
    try {
        binder.writeBean(selectedCustomer);   // validates, then copies into the bean
        grid.getDataProvider().refreshAll();
    } catch (ValidationException e) {
        // errors are already shown on the fields
    }
});

var discard = new Button("Discard");
// Buffered mode payoff: re-reading the bean throws away every unsaved edit.
discard.addClickListener(event -> binder.readBean(selectedCustomer));
```

Spoken, never shown: the alternative `setBean()` is *unbuffered* — every keystroke writes straight
into the bean, no explicit save/discard.

---

## Step 8 — Chapter 11: Add & delete — completing CRUD with dialogs

**Concepts by use, not lessons:** component extraction (composition), `Dialog`, `ConfirmDialog`
(~2 lines each). The callback (`setOnSave`) is the listener pattern (C4) on a component you built
yourself.

**Beat 1 — extract the form.** To reuse the form in a create-dialog, it moves into its own class.
`CustomerForm` owns its fields, its `Binder`, and its buttons; the view never touches binding
logic again. Results come back through plain callbacks:

```java
public class CustomerForm extends Composite<FormLayout> {

    private final TextField firstName = new TextField("First name");
    // ...the other fields, the Save/Discard/Delete buttons...

    private final Binder<Customer> binder = new Binder<>(Customer.class);

    private Customer customer;
    private Consumer<Customer> onSave;
    private Consumer<Customer> onDelete;

    public CustomerForm() {
        // ...the chapter-10 bindings move here unchanged...

        save.addClickListener(event -> save());
        discard.addClickListener(event -> binder.readBean(customer));
        delete.addClickListener(event -> {
            if (customer != null && onDelete != null) {
                onDelete.accept(customer);
            }
        });

        getContent().add(firstName, lastName, email, status, customerSince, buttons);
    }

    /** Loads a customer into the form. Pass {@code null} to clear it. */
    public void setCustomer(Customer customer) {
        this.customer = customer;
        binder.readBean(customer);
        // Delete only makes sense for a customer that has already been saved.
        delete.setVisible(customer != null && customer.getId() != null);
    }

    public void setOnSave(Consumer<Customer> onSave) { this.onSave = onSave; }
    public void setOnDelete(Consumer<Customer> onDelete) { this.onDelete = onDelete; }

    private void save() {
        try {
            binder.writeBean(customer);
            if (onSave != null) {
                onSave.accept(customer);
            }
        } catch (ValidationException e) {
            // Validation errors are shown on the fields automatically.
        }
    }
}
```

The view shrinks to wiring:

```java
final CustomerForm form = new CustomerForm();

private void configureForm() {
    form.setOnSave(customer -> {
        // still the hardcoded list here — the real save arrives in ch. 12
        updateList();
        editCustomer(null);
    });
    form.setOnDelete(this::confirmDelete);
}

private void editCustomer(Customer customer) {
    if (customer == null) {
        grid.deselectAll();
        form.setVisible(false);
    } else {
        form.setCustomer(customer);
        form.setVisible(true);
    }
}
```

**Beat 2 — create, via Dialog.** A toolbar button opens a *fresh* `CustomerForm` instance in a
`Dialog` with a fresh bean — same class, zero duplication. It starts as a text button:

```java
var newButton = new Button("Add Customer", event -> openCreateDialog());
```

…and in the same chapter swaps to icon-only — the "icons are easy" moment:

```java
var newButton = new Button(VaadinIcon.PLUS.create(), event -> openCreateDialog());
newButton.addThemeVariants(ButtonVariant.PRIMARY);
newButton.setAriaLabel("Add customer");
```

```java
private void openCreateDialog() {
    var dialogForm = new CustomerForm();
    dialogForm.setCustomer(new Customer());

    var dialog = new Dialog();
    dialog.setHeaderTitle("New customer");
    dialog.add(dialogForm);

    dialogForm.setOnSave(customer -> {
        updateList();
        dialog.close();
    });

    dialog.open();
}
```

**Beat 3 — delete, via ConfirmDialog:**

```java
private void confirmDelete(Customer customer) {
    var confirm = new ConfirmDialog();
    confirm.setHeader("Delete customer?");
    confirm.setText("Are you sure you want to delete %s %s?"
            .formatted(customer.getFirstName(), customer.getLastName()));
    confirm.setCancelable(true);
    confirm.setConfirmText("Delete");
    confirm.setConfirmButtonTheme("error primary");
    confirm.addConfirmListener(event -> {
        // real delete arrives in ch. 12
        updateList();
        editCustomer(null);
    });
    confirm.open();
}
```

CRUD is now complete against the in-memory list.

---

## Step 9 — Chapter 12: Make it real — connect a database

**Concept introduced: C8 — views call plain Java services. No REST layer.** This pays off the
chapter-2 claim: no endpoints, no DTOs, no fetch calls.

**Beat 1 — tour, don't build.** Open the `backend` package for the first time:

```
backend/
├── Customer.java            @Entity — those annotations map it to the customer table
├── CustomerRepository.java  extends JpaRepository — Spring generates the implementation
├── CustomerService.java     findAll() / save() / delete() — the API the UI calls
└── Status.java
resources/
└── data.sql                 37 sample customers, loaded into H2 on startup
```

```java
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<Customer> findAll() { ... }

    @Transactional
    public Customer save(Customer customer) { ... }

    @Transactional
    public void delete(Customer customer) { ... }
}
```

**Beat 2 — the swap.** The view asks Spring for the service in its constructor (one sentence:
that's dependency injection at work — Spring hands it in):

```java
private final CustomerService customerService;

CustomerListView(CustomerService customerService) {
    this.customerService = customerService;
    // ...
}
```

Then the data source swaps — and this is the whole diff:

```java
// in updateList():
List<Customer> customers = customerService.findAll();   // was: getSampleCustomers()

// in configureForm() / openCreateDialog():
customerService.save(customer);                          // added before updateList()

// in confirmDelete():
customerService.delete(customer);                         // added before updateList()
```

Delete `getSampleCustomers()`. The UI code barely changed — that's the architecture lesson.
Refresh the page: the data is still there.

---

## Step 10 — Chapter 13: UI Shell and Navigation

**Concept introduced: C9 — an app shell is a layout that wraps every view** (`@Layout`), with
composition (C3) revisited at app scale. First work outside a view class.

Motivation: two views exist, but `/customers` is reachable only by typing the URL.

New class `MainLayout` — the complete file:

```java
@Layout
public final class MainLayout extends AppLayout {

    MainLayout() {
        setPrimarySection(Section.DRAWER);

        var appLogo = VaadinIcon.VAADIN_H.create();

        var appName = new Span("Polaris CRM");

        var header = new HorizontalLayout(appLogo, appName);
        header.setPadding(true);

        var nav = new SideNav();
        nav.addItem(new SideNavItem("Home", HomeView.class, VaadinIcon.HOME.create()));
        nav.addItem(new SideNavItem("Customers", CustomerListView.class, VaadinIcon.USERS.create()));

        addToDrawer(header, nav);
    }
}
```

- `@Layout` tells Vaadin: wrap every view in this. Neither view changes — refresh, and both are
  framed.
- `AppLayout` is a component with drawer/navbar slots; `SideNav` items point at view *classes* —
  no URL strings.
- The frame is just another component tree: header + nav composed with the same layouts as ch. 5.

*(The `.app-name` CSS class shown in the final file arrives in chapter 14, beat 3.)*

---

## Step 11 — Chapter 14: Styling — make it look professional

*No new core concept — the visual-payoff chapter, three beats, Java-first.*

**Beat 1 — polish what we built, pure Java.** Theme variants got used along the way
(`ButtonVariant.PRIMARY` on Save and the add-button, `ERROR`+`TERTIARY` on Delete); recap them
as *the* first answer to "how do I style this?" Then spacing and sizing polish on the Customers
view — a proper toolbar with a title, and a width cap on the form:

```java
// CustomerListView — toolbar gets a title pushed to the left edge:
var title = new H3("Customers");
var toolbar = new HorizontalLayout(title, filter, newButton);
toolbar.setWidthFull();
toolbar.setFlexGrow(1, title);
```

```java
// CustomerForm — Java sizing API:
getContent().setMaxWidth("300px");
```

**Beat 2 — theming.** Aura vs Lumo in one sentence. Color schemes get a spoken mention only:
`@ColorScheme(ColorScheme.Value.DARK)` on the app shell class, or `Page::setColorScheme()` at
runtime — no toggle built. Then the Aura **theme builder**
(https://vaadin.github.io/web-components/aura.html): pick the *Sunset Glass* preset (Light),
copy, paste into `styles.css` — the whole app re-skins in one paste:

```css
@import url('https://fonts.googleapis.com/css2?family=Manrope:wght@200..800&display=swap');

html {
  --aura-accent-color-dark: #615FFF;
  --aura-accent-color-light: #4F39F6;
  --aura-background-color-dark: #0C0B2F;
  --aura-background-color-light: #D0DAFF;
  --aura-base-font-size: 15;
  --aura-base-radius: 7;
  --aura-font-family: 'Manrope', var(--aura-font-family-system);
  --aura-surface-level: 2;
}
/* ...plus the generated font-weight overrides... */
```

One sentence: *these are CSS variable overrides* — which sets up beat 3.

**Beat 3 — how CSS fits (brief).** Exactly one example: a class name applied in Java, one rule in
`styles.css`. Plain values on purpose; CSS variables are mentioned, not used:

```java
// MainLayout:
appName.addClassName("app-name");
```

```css
.app-name {
    font-weight: bold;
    font-size: 1.2rem;
}
```

CSS is the power tool — the full branding story is the "Brand Your Vaadin App" video (tease).

---

## Done

The app now matches `tutorial-complete`: two views in a themed shell, full CRUD against H2,
navigation, and polish. Chapter 15 wraps up the concept arc (C1–C9) and names the sequels:
Binder deep-dive, master-detail, dynamic menus (`@Menu`), route parameters.
