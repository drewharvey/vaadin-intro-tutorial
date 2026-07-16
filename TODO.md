# TODO

- [x] Think of a better way to demonstrate custom CSS than the customer form
      — **resolved:** the `.app-name` rule styling the shell's app name (plain CSS
      values; CSS variables mentioned but not used). Form CSS removed; the
      color-coded-status idea was dropped (needed a renderer or `::part()` selectors).
- [x] Show both custom CSS and the Java API for styles
      — **resolved via ch. 14 structure:** beat 1 is Java-API polish (e.g.
      CustomerForm's `setMaxWidth("300px")`), beat 3 is the `.app-name` CSS rule.
      Confirm the two get contrasted out loud when scripting ch. 14.
- [x] Add more customers (data.sql seed data) — 30 added, 37 total
- [ ] See if we can make the edit details panel look better (go over the table?)
- [ ] Views have no browser tab title since dropping `@PageTitle` — check how the
      tab looks on camera; decide whether to set a title some other way or ignore.
