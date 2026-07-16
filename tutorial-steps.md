# Tutorial steps: from starter to Polaris CRM

How the code gets from the **`main` branch** (starter: empty `HomeView`, dormant `backend` package)
to the **`tutorial-complete` branch** (the finished app), chapter by chapter, following
`2027-06-vaadin-intro-tutorial.md`.

Each step shows the code written or changed in that chapter — block-level changes and the
significant parts, not every line. **First-time concepts are called out at the top of each step.**
The final state of every file matches `tutorial-complete` exactly; the intermediate snapshots are
reconstructions that follow the outline's teaching decisions (manual tedium before Binder,
`setBean` before buffered mode, text button before icon, plain components before any styling).

Two standing rules across chapters 4–13: **no theme variants, custom CSS classes, or
accessibility annotations** — every styling API waits for chapter 14 — and temporary "prove it"
code (the route-test button, the auto-column grid) is shown, then removed.

Chapters 1–2 (intro, "What is Vaadin?") and 15 (wrap-up) have no code and are omitted here apart
from their concept notes.

---

## Step 0 — Chapter 3: Project setup

*No code written. Concepts: none (Vaadin Copilot/dev-mode indicator acknowledged in one sentence — "part of dev mode, ignore it for this tutorial").*

Download the starter and open it in the IDE. **Run it from the IDE** (the Run button on
`Application.java`) — that's the workflow used for the rest of the tutorial. Mention the
alternative for the terminal-inclined, since no local Maven is needed:

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

`@Route("")` maps it to the root URL. No throwaway hello here — go straight to the first real
component. Add a `TextField` and watch the page change:

```java
HomeView() {
    var name = new TextField("Name");
    add(name);
}
```

That one field *is* the start of the chapter 5–6 scene. The page is bare on purpose: the frame
around it — the app shell — is built in chapter 13.

---

## Step 2 — Chapter 5: Components & layouts

**Concept introduced: C3 — components are UI elements, layouts arrange them, `add()` composes,
and layouts nest** (a layout *is* a component — stated here, shown concretely in chapter 6).

Add a button under the field. The view extends `VerticalLayout`, so added components stack top
to bottom:

```java
HomeView() {
    var name = new TextField("Name");
    add(name);

    var sayHelloBtn = new Button("Say hello");
    add(sayHelloBtn);
}
```

On screen: a text field with a button beneath it. It doesn't do anything yet — that's the next
chapter, one continuous scene across the cut.

---

## Step 3 — Chapter 6: Listeners — making it interactive

**Concepts introduced: C4 — listeners are *the* interaction pattern ("they all look like this"),
and C5 — field values via `getValue()`/`setValue()`.** Lambda syntax gets a brief explanation at
first use. C3 gets reinforced at the end.

**First:** wire the button up — read the field, show feedback:

```java
var sayHelloBtn = new Button("Say hello");
sayHelloBtn.addClickListener(event -> {
    Notification.show("Hello " + name.getValue());
});
```

`Notification` is Tier 3: "the feedback tool," one sentence. The generalization moment lands
here: every Vaadin component reacts through listeners, and they all look like this —
`addSomethingListener(event -> ...)`.

**Then:** "let's add a button to clear the name field." The Reset button *writes* the field —
`setValue("")` — and grouping the two buttons side by side reinforces the layout model from
chapter 5 (a `HorizontalLayout` nested inside the view's `VerticalLayout`):

```java
var resetBtn = new Button("Reset");
resetBtn.addClickListener(event -> {
    name.setValue("");
});

var buttonsLayout = new HorizontalLayout();
buttonsLayout.add(sayHelloBtn, resetBtn);
add(buttonsLayout);
```

`HomeView` is now finished; it stays exactly like this in the final app.

---

## Step 4 — Chapter 7: Display data in a Grid

**Concept introduced: C6 — Grid** (the ~3-lines credibility moment). **C2 revisited:** this is a
*new, second* view.

**First, prove the route.** Create `CustomerListView` in the `ui` package with just a button in
it, then type `/customers` in the browser — the view appears. That's `@Route` doing its job:

```java
@Route("customers")
class CustomerListView extends VerticalLayout {

    CustomerListView() {
        add(new Button("I'm the customers view!"));
    }
}
```

Remove the button. **Then bring in the data** — a hardcoded list of the `Customer` class from
the `backend` package, used here as a plain POJO:

```java
private List<Customer> getSampleCustomers() {
    return List.of(
            new Customer("Alice", "Nguyen", "alice.nguyen@meridian-labs.com",
                    Status.CUSTOMER, LocalDate.of(2023, 1, 15)),
            new Customer("Bob", "Martinez", "bob.martinez@bluefern.io",
                    Status.CUSTOMER, LocalDate.of(2022, 11, 3)),
            new Customer("Carol", "Schmidt", "carol.schmidt@meridian-labs.com",
                    Status.PROSPECT, null));
}
```

**Now the wow:** give Grid the class and it generates the columns by itself:

```java
var grid = new Grid<>(Customer.class);
grid.setItems(getSampleCustomers());
add(grid);
```

A full data table from three lines. **Then take control:** auto-columns show everything
(including `id`) in bean order — for real apps you usually pick the columns yourself. Switch to
a plain `Grid` and declare them:

```java
final Grid<Customer> grid = new Grid<>();

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
```

Two acknowledgment beats, one sentence each:

- `Customer` carries JPA annotations — *"ignore those for now; they're how this becomes a
  database table — that's chapter 12."*
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

**Start the filter minimal** — a bare field wired to a method that decides what the grid shows:

```java
final TextField filter = new TextField();

private HorizontalLayout createToolbar() {
    filter.addValueChangeListener(event -> updateList());
    return new HorizontalLayout(filter);
}
```

**First version of `updateList()` — first name only,** to see it work end to end:

```java
private void updateList() {
    List<Customer> customers = getSampleCustomers();

    var query = filter.getValue();
    if (query != null && !query.isBlank()) {
        var lower = query.toLowerCase();
        customers = customers.stream()
                .filter(customer -> customer.getFirstName().toLowerCase().contains(lower))
                .toList();
    }

    grid.setItems(customers);
}
```

(The stream gets one sentence: keep the customers whose name contains the search text.)

**Then broaden it** — modern search matches more than a name:

```java
.filter(customer -> customer.getEmail().toLowerCase().contains(lower)
        || customer.getFirstName().toLowerCase().contains(lower)
        || customer.getLastName().toLowerCase().contains(lower)
        || customer.getStatus().toString().toLowerCase().contains(lower))
```

**Final step — polish the field:** a placeholder, and `ValueChangeMode.LAZY` ("fire while
typing, but only after a pause"):

```java
filter.setPlaceholder("Search...");
filter.setValueChangeMode(ValueChangeMode.LAZY);
```

The constructor now does `add(createToolbar(), grid)` and calls `updateList()` instead of
`grid.setItems(...)`.

---

## Step 6 — Chapter 9: Show item details

**No new concept — C4 revisited again** (third listener type: *selection*) **and C5 revisited —
deliberately painfully.** This chapter builds the anti-pattern that Binder kills in chapter 10.

Add detail fields beside the grid. `FormLayout` is Tier 3: "a layout that arranges fields nicely."
`ComboBox` and `DatePicker` are just fields — same `setValue`/`getValue` contract:

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

A `Binder` connects the fields to the bean's getters/setters once; from then on it moves the
data both ways. Validation happens while binding — required fields plus one real rule:

```java
private final Binder<Customer> binder = new Binder<>(Customer.class);

// in the constructor:
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

**Start with the simplest mode — `setBean` (unbuffered).** The five manual `setValue()` lines
collapse into one, and every edit writes straight through into the bean as you type:

```java
grid.addSelectionListener(event -> {
    var customer = event.getFirstSelectedItem().orElse(null);
    binder.setBean(customer);   // was: five setValue() calls
});
```

Show it working: edit a name, and the bean already has the change. **Then motivate the switch:**
we want the user to *decide* — click Save, or throw the edits away. That's *buffered* mode:
`readBean` copies values into the fields; `writeBean` copies them back only if validation passes:

```java
grid.addSelectionListener(event -> {
    var customer = event.getFirstSelectedItem().orElse(null);
    binder.readBean(customer);   // buffered: fields hold a copy until you write back
});

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

No styling on the buttons yet — they're plain until chapter 14.

---

## Step 8 — Chapter 11: Add & delete — completing CRUD with dialogs

**Concepts by use, not lessons:** component extraction (composition), `Dialog`, `ConfirmDialog`.
One acknowledging sentence: Vaadin has a more robust mechanism for custom component events — for
this tutorial we keep it simple with single callbacks, which still follow the same event-driven
component concept (C4).

**Beat 1 — extract the form.** To reuse the form in a create-dialog, it moves into its own class.
`CustomerForm` owns its fields, its `Binder`, and its buttons; the view never touches binding
logic again:

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
`Dialog` with a fresh bean — same class, zero duplication. It starts as a text button…

```java
var newButton = new Button("Add Customer", event -> openCreateDialog());
```

…and in the same chapter swaps to icon-only — the "icons are easy" moment (no theme variants —
those wait for chapter 14):

```java
var newButton = new Button(VaadinIcon.PLUS.create(), event -> openCreateDialog());
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

**Beat 3 — delete.** Start honest: the Delete button just deletes, immediately:

```java
private void confirmDelete(Customer customer) {
    // deletes with no warning — is that what we want?
    updateList();
    editCustomer(null);
}
```

Then ask the question out loud — *shouldn't the user confirm an action like this?* — and reach
for `ConfirmDialog`, most basic form first:

```java
var confirm = new ConfirmDialog();
confirm.setHeader("Delete customer?");
confirm.addConfirmListener(event -> {
    // real delete arrives in ch. 12
    updateList();
    editCustomer(null);
});
confirm.open();
```

Then show a bit of its API — a proper message and a cancel path:

```java
confirm.setText("Are you sure you want to delete %s %s?"
        .formatted(customer.getFirstName(), customer.getLastName()));
confirm.setCancelable(true);
confirm.setConfirmText("Delete");
```

CRUD is now complete against the in-memory list.

---

## Step 9 — Chapter 12: Make it real — connect a database

**Concept introduced: C8 — views call plain Java services. No REST layer.** This pays off the
chapter-2 claim: no endpoints, no DTOs, no fetch calls.

**Beat 1 — tour, don't build.** Open the `backend` package for the first time. It's been sitting
there since chapter 3:

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
customerService.delete(customer);                        // added before updateList()
```

Delete `getSampleCustomers()`. The UI code barely changed — that's the architecture lesson.
Refresh the page: the data is still there.

---

## Step 10 — Chapter 13: UI Shell and Navigation

**Concept introduced: C9 — an app shell is a layout that wraps every view** (`@Layout`), with
composition (C3) revisited at app scale. First work outside a view class.

Motivation: two views exist, but `/customers` is reachable only by typing the URL.

**Beat 1 — the frame.** New class `MainLayout` with just the logo and app name; then straight to
the browser — *both* views are suddenly wrapped in it, and neither view changed:

```java
@Layout
public final class MainLayout extends AppLayout {

    MainLayout() {
        setPrimarySection(Section.DRAWER);

        var appLogo = VaadinIcon.VAADIN_H.create();

        var appName = new Span("Polaris CRM");

        var header = new HorizontalLayout(appLogo, appName);
        header.setPadding(true);

        addToDrawer(header);
    }
}
```

- `@Layout` tells Vaadin: wrap every view in this.
- `AppLayout` is a component with drawer/navbar slots; the frame is just another component tree,
  composed with the same layouts as chapter 5.

**Beat 2 — the navigation.** A `SideNav` whose items point at view *classes* — no URL strings:

```java
var nav = new SideNav();
nav.addItem(new SideNavItem("Home", HomeView.class, VaadinIcon.HOME.create()));
nav.addItem(new SideNavItem("Customers", CustomerListView.class, VaadinIcon.USERS.create()));

addToDrawer(header, nav);
```

*(The `.app-name` CSS class in the final file arrives in chapter 14, beat 3.)*

---

## Step 11 — Chapter 14: Styling — make it look professional

*No new core concept — the visual-payoff chapter, four beats, Java-first. This is where styling
APIs appear for the first time: chapters 4–13 used none.*

**Beat 1 — polish what we built, pure Java.** Theme variants debut — the first answer to "how do
I style this?":

```java
// CustomerForm — the form's buttons:
save.addThemeVariants(ButtonVariant.PRIMARY);
delete.addThemeVariants(ButtonVariant.ERROR, ButtonVariant.TERTIARY);

// CustomerListView — the add-button:
newButton.addThemeVariants(ButtonVariant.PRIMARY);

// confirmDelete() — the dialog's confirm button:
confirm.setConfirmButtonTheme("error primary");
```

Then spacing and sizing polish — a proper toolbar with a title, and a width cap on the form:

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

**Beat 2 — theming.** Toggle the whole app between Aura and Lumo by swapping one line in
`Application.java` — everything re-themes, nothing breaks:

```java
@StyleSheet(Aura.STYLESHEET)   // ⇄ swap with Lumo.STYLESHEET, then back
```

Then Aura's light and dark color schemes, flipped the same way:

```java
@ColorScheme(ColorScheme.Value.DARK)   // on the Application class
```

Mention: `Page::setColorScheme()` does the same at runtime — you could let your users decide.

**Beat 3 — how CSS fits (brief).** Exactly one example: a class name applied in Java, one rule
in `styles.css`. Plain values on purpose; CSS variables are mentioned, not used:

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

**Beat 4 — the Aura theme builder (the fun extra).** Show off the tool
(https://vaadin.github.io/web-components/aura.html) — presets, randomize, customize — then pick
the *Sunset Glass* preset (Light), copy, and paste into `styles.css`. The overrides are CSS
variables — recognizable from beat 3 — and the whole app re-skins in one paste:

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

---

## Done

The app now matches `tutorial-complete`: two views in a themed shell, full CRUD against H2,
navigation, and polish. Chapter 15 wraps up the concept arc (C1–C9) and names the sequels:
Binder deep-dive, master-detail, dynamic menus (`@Menu`), route parameters.
