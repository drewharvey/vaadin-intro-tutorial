# Step-validation notes (branch: step-validation, 2026-07-16)

Every step from `tutorial-steps.md` was implemented in order on this branch — one commit per
step/beat, including the throwaway intermediate states — and verified (compile per step, tests
where applicable, browser checks at the visual milestones). **Final result: the end state is
byte-identical to `tutorial-complete`** except for Javadoc blocks and `CustomerListViewTest`,
neither of which is on-camera code. The steps genuinely build the app.

Two fixes were required along the way, plus a stack of observations, sorted below.

---

## 🔴 Bugs — the steps as documented don't work without these fixes

1. **Ch. 11: the Delete button is invisible for every customer.** `CustomerForm` guards with
   `customer.getId() != null`, but every hardcoded POJO has a null id until ch. 12 — the whole
   delete/ConfirmDialog beat is undemonstrable as written. **Fix used on this branch:** ch. 11
   uses `delete.setVisible(customer != null)`; ch. 12 re-introduces the id-guard as a refinement,
   with a ready-made narrative hook — in ch. 11 the *create dialog* visibly shows a (dead) Delete
   button, and ch. 12's "now customers have real ids" fixes it meaningfully.

2. **Ch. 8–11: the hardcoded list must be stateful.** As documented, `updateList()` calls
   `getSampleCustomers()` fresh every time, so every save/add/delete silently vanishes on the
   next refresh (even ch. 10's saved edit is lost the moment you touch the filter). **Fix used
   on this branch:** a field — `private final List<Customer> customers = new
   ArrayList<>(getSampleCustomers());` — that `updateList()` reads, the dialog adds to, and
   delete removes from. Introduce the field in ch. 8 when `updateList()` first appears. Bonus:
   the ch. 12 swap stays exactly as clean (`customers` field → `customerService.findAll()`).

3. **Ch. 10 doc gaps:** `binder.writeBean(selectedCustomer)` uses a `selectedCustomer` field the
   doc never declares, and the Save/Discard buttons are never shown being added to a layout.
   Both need one code line each in the steps doc.

4. **Dialog close-X is orphaned.** The final code has a close button (with `TERTIARY`) in the
   dialog header, but no chapter adds it. Options: add it in ch. 14 beat 1 (where its variant
   lives anyway), or **drop it entirely** — Dialog already closes on Esc and backdrop-click, and
   that's one less block of non-teaching code (see #12).

5. **`@ColorScheme` import:** the annotation lives in `com.vaadin.flow.component.page` (not the
   `theme` package). Verified compiling, as is `Lumo.STYLESHEET`
   (`com.vaadin.flow.theme.lumo.Lumo`) — the ch. 14 beat-2 toggle works as scripted.

## 🟢 Verified teaching beats (worked exactly as scripted)

- Auto-columns (`new Grid<>(Customer.class)`): columns come out **alphabetical** with an awkward
  empty **Id** column — the "then take control" motivation is real and visible on screen.
- The ch. 11 CRUD cycle (edit+save, dialog create, confirm delete) works end-to-end in the
  browser against the stateful list.
- The setBean → buffered switch, the route-proof button, the frame-then-nav shell build, and the
  one-paste re-theme all verified.

## ✂️ Simplification opportunities

6. **`grid.getDataProvider().refreshAll()` (ch. 10 save)** — introduces a whole new concept
   (data providers) for one interim line. `updateList()` already exists since ch. 8 and does the
   job with zero new API. Recommend swapping.
7. **`if (query != null && !query.isBlank())`** — `TextField.getValue()` never returns null
   (it's documented empty-string), so the null-check is dead code teaching a wrong worry.
   `if (!query.isBlank())` is shorter and honest.
8. **`setPrimarySection(Section.DRAWER)`** — one line that needs a sentence to explain and has
   little visible effect in an app with no navbar content. Try dropping it; if the drawer looks
   the same, cut it.
9. **ConfirmDialog options** — `setCancelable`/`setConfirmText` earn their place in the ch. 11
   "show a bit of the API" beat; `setConfirmButtonTheme` correctly deferred to ch. 14. Matches
   the new rules; just make sure the outline says so too.

## 🧠 Cognitive-load flags

10. **Ch. 10 is the heaviest chapter**: a 15-line bindings block + mode switch + validation +
    try/catch. Consider binding just `firstName`/`lastName` on camera, fast-forwarding the rest,
    and giving `ValidationException` its one scripted sentence ("writeBean refuses to write
    invalid data — the exception is how it tells us").
11. **`Composite<FormLayout>`** — the extraction introduces generics + the `getContent()`
    indirection at the same moment as callbacks. Alternative: `extends FormLayout` directly —
    less correct as practice, but removes both concepts. Worth a deliberate decision either way.
12. **Ch. 11 is the longest chapter** (extraction + dialog + delete arc + icon swap). If it needs
    slimming, the text→icon button swap is the movable beat (styling-adjacent → ch. 14 beat 1).

## 💡 Missed teaching opportunities

13. **Auto-columns are sortable by default; explicit `addColumn` ones are not.** Viewers may
    notice sorting *disappear* between ch. 7's auto-grid and the explicit one. Turn it into the
    ch. 8 segue: "when you define columns yourself, you opt into sorting — one method."
14. **The manual form never handles deselection** (fields keep stale values) — one spoken line
    in ch. 9 adds it to the pain list, and Binder's `readBean(null)` clearing everything for
    free becomes one more relief beat in ch. 10.
15. **The dead Delete button in the ch. 11 create dialog** (after fix #1) is a feature, not a
    bug: point at it, promise the fix, deliver it in ch. 12 with the id-guard. Bookends nicely.

## Branch anatomy

One commit per step/beat on `step-validation` (main → …): step 1 TextField, step 2 button,
step 3 listeners+Reset, 4a route-proof, 4b auto-columns, 4c explicit columns, 5a minimal filter,
5b broadened+polish, 6 manual form, 7a setBean, 7b buffered, 8 extraction+CRUD (+ delete-guard
fix), 9 service swap (+ id-guard restore), 10a frame, 10b nav, 11 styling. Screenshots of the
milestones are in the session scratchpad.
