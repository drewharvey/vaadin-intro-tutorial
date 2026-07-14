# Vaadin Introduction Tutorial — from "What is Vaadin?" to a working app

- **Format:** Long-form
- **Goal:** Adoption
- **Bet:** TBD
- **Style:** TBD (Presenter | Voiceover)
- **Series:** none
- **Status:** Scripting

## Value statement

After watching, a Java developer understands what Vaadin is and how it works (views, components, layouts, listeners, Binder) and has built their first Vaadin app: a full Grid-based CRUD UI — sorting, filtering, edit with validation, add via dialog, delete with confirmation — connected to a real database through plain Java services (Spring Data JPA + H2, pre-baked in the starter), then polished in three styling passes: Java-only polish (theme variants, layout spacing), theming (Aura color schemes via the theme builder), and a first taste of CSS. A standalone concept-first curriculum scoped for a true first app; deliberately long.

## Chapter outline (concept-first curriculum — the docs getting-started tutorial was the brainstorm seed, not a spec; this video stands alone)

**Audience assumption:** viewer does not know how Vaadin works, and we can't assume their Java expertise level — explain Vaadin concepts from zero, keep Java itself simple and don't lean on advanced language features.

One concept per chapter (the chapter list is the curriculum — each chapter states its concept payload in one line or it's merged/split). Chapter titles double as YouTube chapter markers: searchable vocabulary where natural.

| # | Chapter | Concept payload | On-screen result |
|---|---|---|---|
| 1 | **Intro: what we're building** | — (Bet 5 cold open: finished app in ~30s, no preamble) | The end result, working |
| 2 | **What is Vaadin?** | **C1: UI = Java objects on the server** + the "how does Java become a web page" beat. Brief — defers to existing explainer content | Diagram/animation beat |
| 3 | **Project setup** | — (download starter → open in IDE → first run) | Empty app running in browser |
| 4 | **Your first view** | **C2: views & `@Route`** — "where does my code go." Includes the shell beat: the frame around the view is the starter's pre-baked MainLayout (app name/logo + main nav) — *pointed at, not built* | "Hello"-level view at a URL, inside the shell |
| 5 | **Components & layouts** | **C3: components are UI elements, layouts arrange them, `add()` composes, layouts nest** | TextField + Button laid out on screen |
| 6 | **Listeners: making it interactive** | **C4 + C5: click listener, `getValue()`, and the "they all look like this" generalization** | Click → Notification with the field's value |
| 7 | **Display data in a Grid** | **C6: Grid** — the ~3-lines-of-Java credibility moment. Hardcoded list of the *entity class* used as a plain POJO — so ch. 12 swaps only the data source, never the model | POJO list rendered as a data table |
| 8 | **Sorting & filtering** | C4 revisited: value change listener, now motivated (filter field) | Live-filtering grid |
| 9 | **Show item details** | C4 revisited (grid selection listener) + C5 revisited: **the deliberate manual tedium** — `setValue()` per field to populate a plain FormLayout | Click row → details populate |
| 10 | **Edit & save with Binder** | **C7: Binder as the relief** — bind the detail fields, one validation rule, `readBean`/`writeBean`, plus a Discard button (just `readBean` again — the buffered-mode payoff). One sentence contrasts `setBean` (unbuffered: edits write through instantly) so the choice is explained, not asserted. Kills the anti-pattern the viewer just felt | Validation firing, edits saving to the grid, discard reverting them |
| 11 | **Add & delete: completing CRUD with dialogs** | C7 payoff + dialogs by use: extract the form into its own class (a composition beat, kept brief) and open it in a **Dialog** for create — the standard enterprise pattern (Grid + "New" button → form dialog). Same form + Binder, fresh bean. Then a delete button with **ConfirmDialog**. Dialog/ConfirmDialog get ~2 lines each — taught by use, not a lesson | "New" → dialog form → row appears; delete → confirm → row gone. Full CRUD |
| 12 | **Make it real: connect a database** | **C8: views call plain Java services — no REST layer** (C1's proof, bookending the ch. 2 claim). Two beats: brief tour of the pre-baked backend (H2 + `data.sql`, entity, repository, service — a tour, not a tutorial), then the swap: `setItems(service.findAll())`, save/delete via `service.save()`/`service.delete()` — the UI barely changes | Refresh the page — the data is still there |
| 13 | **Styling: make it look professional** | — (the visual-payoff chapter, no new core concept): three beats, Java-first. **Beat 1 — polish what we built:** layout spacing/positioning + component theme variants, pure Java. **Beat 2 — theming:** Aura vs Lumo in a sentence, color schemes (the dark-mode toggle lives here), and the **Aura theme builder** (Jouni's tool — presets / randomize / customize → copy the generated CSS var overrides: https://vaadin.github.io/web-components/aura.html); the whole app re-skins in one paste. **Beat 3 — how CSS fits (brief):** one small example — a CSS class applied via `addClassNames(...)` — enough to understand the var overrides just pasted and future branding; tease the theme-customization video for the full story | Same app, visibly polished; the dark-mode flip; the one-paste re-theme |
| 14 | **Wrap-up & next steps** | — (recap the concept arc, name the sequels, CTA) | — |

The listener thread is visible in the chapter list itself: ch. 6 teaches the pattern, ch. 8 and ch. 9 each reuse it with a new listener type. The form arc is tedium (ch. 9) → relief (ch. 10) → payoff (ch. 11). The data arc is claim (ch. 2: "no REST layer") → hardcoded stand-in (ch. 7–11) → proof (ch. 12). Styling comes last deliberately: the audience is backend Java devs — functionality validated against a realistic backend outranks polish, and styling is the cherry on top, sending the viewer into the wrap-up looking at the finished, polished app.

Inspiration/starting point (not followed): https://vaadin.com/docs/latest/getting-started/tutorial

**Scope decisions (what's in, what's out, and why):**
- **Flat, simple entity/POJO** — a handful of String/number fields; no relations, no nested objects, no clever types. The domain model must never compete with the UI concepts for attention — and a flat entity keeps Grid columns, form fields, Binder bindings, and `data.sql` trivially readable.
- **No master-detail layout** — details shown with plain fields/FormLayout instead; simpler mental model for a first app.
- **Staged data: hardcoded first, real at the end** — the starter ships a pre-baked backend (H2 + `data.sql`, entity, repository, service), *ignored* until ch. 12. Chapters 7–11 use a hardcoded list of the entity class (as a plain POJO) so the Grid/form chapters stay about the UI, and ch. 12 swaps only the data source. Persistence is **toured, never built** — watching setup is where beginner tutorials die; Spring devs will recognize the shape instantly, newer devs get the general idea.
- **Styling chapter last, three-part structure (ch. 13)** — **(1) Polish what we built**, pure Java: layout spacing/positioning and component theme variants — show how good the app looks with just layouts, Java APIs, and theme variants first; that's the answer to every beginner's first question ("how do I style this?"). **(2) Theming:** Aura vs Lumo positioning in a sentence, color schemes (the dark-mode toggle), and the **Aura theme builder** (https://vaadin.github.io/web-components/aura.html — presets, randomize, customize → copy/paste the generated CSS variable overrides) as the low-effort branding on-ramp. **(3) How CSS fits, brief:** CSS is the power tool, and branding via theme-var overrides needs the basics anyway — one small example (a CSS rule + `addClassNames(...)`), nothing more. CSS never becomes the focus of styling; full theming/branding remains the "Brand Your Vaadin App" mini-tutorial's job (tease it here). Placed after the database chapter: the audience is backend Java devs, so validating the UI against a realistic backend outranks polish — styling is the final cherry on top.
- **MainLayout pre-baked, mentioned not built** — the starter ships a simple MainLayout (app name/logo + main nav); a brief beat in ch. 4 explains that's where the frame comes from. Building the shell on camera would pull time from the core concepts. App stays single-view — nav shows one entry, "more views = more entries" is said, not built.
- **Deliberately long** — this is a cornerstone tutorial; comprehensiveness wins over brevity. The 14 chapter markers keep it navigable and rewatchable.
- **MAYBE — extract Grid into its own class (decide during scripting, once the real code exists):** only viable as a subclass (`class CustomerGrid extends Grid<Customer>`, column config in the constructor) — ~3 lines overhead, no delegation boilerplate since the view still calls `setItems()`/selection directly; a wrapper/Composite would add delegation and fails the boilerplate test. Pro: good habit, clears the view, reinforces composition. Con: grid config is deliberately tiny (the ch. 7 credibility moment), it's a second refactor beat, and it puts a second reuse pattern (extend) next to the form's (compose). Fallback if cut: one spoken line in ch. 11 — "in a bigger app you'd extract the grid too, like the form."
- **Manual→Binder→reuse form arc, with create *after* edit** — details form is deliberately manual (ch. 9, `setValue()` tedium), Binder arrives as the relief (ch. 10), and ch. 11 reuses the same form + Binder with a fresh bean. The anti-pattern appears exactly once before Binder kills it; neither extreme works (Binder-from-start = overload; no Binder = ships an anti-pattern as the viewer's mental model), and putting create after edit keeps the add feature from repeating the manual pattern.
- **CRUD completed with dialogs (ch. 11)** — Add opens the reused form in a Dialog (the pattern every enterprise app uses: Grid + "New" → form dialog); delete confirms via ConfirmDialog. Both taught by use, ~2 lines each. Chosen deliberately: it makes the "CRUD" promise literal and shows both dialog flavors without a dialog lesson. Reusing the form in a Dialog requires extracting it into its own component class — shown as a quick composition beat, not a custom-components lesson.

## Parking lot — undecided ideas (written down, not yet in or out; validate during scripting)

Each item gets resolved to **in** (with a chapter), **out**, or **sequel/next-steps** before recording. One-line cost/fit note per item; ⚠ marks items that conflict with a locked scope decision above — including one means overriding that decision, recorded per the soft-gate rule.

| Idea | Quick read on cost/fit |
|---|---|
| **Keyboard shortcuts** — e.g. `saveButton.addClickShortcut(Key.ENTER)` | One line; natural fit in ch. 10–11 (Enter = save in the form/dialog). Tier-3-style: use, don't teach |
| **More view config** — `@PageTitle`; `@Route` explained deeper; `@Menu` | `@PageTitle` is a cheap one-liner in ch. 4 (browser tab visibly changes). `@Menu` extends the MainLayout mentioned-not-built beat — small but adds nav talk to a single-view app. Deeper `@Route` risks bloating C2 |
| **CSS styling** | **Resolved → in (ch. 13, beat 3):** brief "how CSS fits" beat — one CSS rule + `addClassNames(...)` example after the Java-first polish and theming beats; full branding stays with the "Brand Your Vaadin App" tease |
| **Route parameters** (passing data to a view) | App is single-view, so it needs a motivating destination — pairs naturally with multi-view navigation, which is currently a named sequel topic |
| **File uploads** | New component + backend handling, and the flat entity has no file field — likely its own video |
| **Vaadin icons** | Cheap and visual — e.g. icons on the New/Delete buttons (ch. 11) or as part of the ch. 13 polish pass |
| **Context menu on the grid** | Could carry edit/delete, but adds a second interaction pattern next to the button-based CRUD — maybe a Short instead |
| **Tooltips / popovers** | Tooltip is a one-liner (`setTooltipText`) — could slot into ch. 13 polish; Popover is a bigger component story |
| **Responsive layouts** | Too big for a first tutorial; FormLayout is already responsive by default — could be one spoken acknowledgment if it comes up on screen |

## Concepts to teach (the concept list, not the feature list, is what makes this land)

### Tier 1 — core mental model (the video fails if these don't land)

| # | Concept | The teaching beat | Chapter |
|---|---|---|---|
| 1 | **UI = Java objects on the server** | The umbrella concept. A `Button` is a Java object; call methods on it; the framework syncs the browser. No HTML, no JS, no REST layer to write. Include a brief "how does this Java become a web page" beat — developers distrust magic they can't place. | Stated in ch. 2, reinforced as the spine of ch. 4–6 |
| 2 | **Views & routing** | A view is a Java class; `@Route` maps a URL to it. The viewer's anchor for "where does my code go." | ch. 4 |
| 3 | **Components + layouts** | Components are UI elements; layouts arrange them; compose with `add()`. Key nuance: a layout *is* a component, so composition nests — the whole layout model in one sentence. | ch. 5 |
| 4 | **Listeners = the interaction pattern** | Teach `addClickListener`, then *generalize*: everything reacts through listeners and they all look the same. The generalization moment is worth more than any individual listener. | ch. 6 (click) → ch. 8 (value change) → ch. 9 (grid selection) |
| 5 | **Field values: `getValue()`/`setValue()`** | Taught in the TextField + Button → Notification scene (the scene spans ch. 5–6: build the UI, then wire it up — teaches #3, #4, #5 together). Manual `setValue()` again in ch. 9 — deliberately, to set up Binder. | ch. 6, ch. 9 |

### Tier 2 — the enterprise payoff

| # | Concept | The teaching beat | Chapter |
|---|---|---|---|
| 6 | **Grid** | The credibility moment: a full data table in ~3 lines of Java. `setItems()` + typed columns from a POJO. Selection listener deferred to ch. 9, where it's motivated. | ch. 7 |
| 7 | **Binder** | The relief after ch. 9's manual tedium: "how you'd really do this." Bind a few fields, one-line validation rule, `readBean`/`writeBean`, and a Discard button — discard is just `readBean` again, which is what makes buffered mode *visibly* worth having. Contrast with `setBean` in one sentence (unbuffered: field edits write straight into the bean — no explicit save/discard), spoken only, never used on screen. Then the payoff: ch. 11 reuses form + Binder with a fresh bean for create, inside a Dialog. Deep dive = next-steps pointer (feeds backlog idea #7, Binder validation). | ch. 10–11 |
| 8 | **Views call plain Java services — no REST layer** | C1's proof, landing as the bookend to the ch. 2 claim. Tour the pre-baked backend, then swap the data source: `setItems(service.findAll())`, `service.save()`. The lesson is what *doesn't* change — the UI code — making the architecture seam visible. Say the payoff out loud: no endpoints, no DTOs, no fetch calls. | ch. 12 |

### Tier 3 — use, don't teach (on screen, one sentence max, no dedicated section)

- **Notification** — the feedback tool in the basics demo; keep moving.
- **FormLayout** — "a layout that arranges fields nicely."
- **Custom CSS / theming internals** — ch. 13 beat 3 shows exactly one small example (a CSS class applied via `addClassNames(...)`), and the pasted theme-builder overrides get a one-sentence explanation ("these are CSS variable overrides"); no selector deep-dive, no stylesheet architecture. Aura's "good by default" gets said early; the full theming/branding story belongs to the "Brand Your Vaadin App" mini-tutorial (teased in ch. 13).
- **MainLayout / app shell** — pre-baked in the starter; one beat in ch. 4 ("this frame is the MainLayout — app name and nav live there"), never built on camera.
- **Dialog / ConfirmDialog** — taught by use in ch. 11 (~2 lines each: create, add form, open / confirm-then-delete); no dialog API tour.
- **Form component extraction** — extracting the form into its own class (needed to reuse it in the Dialog) is a quick composition beat in ch. 11, not a custom-components lesson. Design decisions locked: the form class *owns its Binder* (view never touches it — the extraction simplifies, not complicates); save is reported back via a **plain lambda callback** (`setOnSave(...)`), not custom component events — it reuses the listener pattern (C4) on the viewer's own component; the inline details area and the dialog each hold their **own instance** of the form class.
- **JPA/Spring Data internals** — the ch. 12 backend is toured, not taught; no annotation-by-annotation walkthrough.
- Explicitly **not** covered (per the scope decisions): persistence *setup*, multi-view navigation, push, Hilla/React.

### Teaching principles (apply across all chapters)

- **Introduce each concept where it's motivated**, never ahead of need (value change → filtering; selection → details; Binder → after manual pain).
- **Never use a Vaadin term before it's defined.** Viewer knows zero Vaadin.
- **Unknown Java level:** briefly explain lambdas at first listener use; no streams/generics gymnastics anywhere.
- **Every chapter ends with something visibly changed on screen** — retention is the weak spot (Bet 5); no chapter is pure setup.

## Packaging (draft BEFORE production — the title defines the promise, the promise defines the hook)

- **Working title (phrased as a search query where possible):**
- **Target query/queries:**
- **Query verification (required — check `keywords.md` register first; probe only unlisted phrases and write results back to the register):**
- **If not searchable → explicit awareness rationale:**
- **Thumbnail concept:**
- **Thumbnail text (≤3–4 words, states the payoff, shares no significant words with the title):**
- **Title = promise check:** does the video deliver exactly what the title claims, fast?

## Production notes

- Outline / script link: (this file, chapter outline above)
- Days logged (rough, min 1):

## Quality bar

All formats:
- [ ] Code runs on current Vaadin version
- [ ] Screen recordings crisp, readable font sizes
- [ ] UI reflects Vaadin polish (Aura where applicable)
- [ ] Clean audio

Long-form:
- [ ] Scripted / tight outline
- [ ] Chapters + UTM-tagged description links + brand thumbnail
- [ ] Edited transitions, not raw cuts
- [ ] If series episode: intro card/outro signal + added to series playlist

## Extended gates — this video only

Beyond the standard bar: this video carries a concept curriculum, a deliberate teaching arc, and several deliberate deltas — a lot to silently drift on between script, recording, and edit. Soft gates per house rules: any can be overridden for cause, but the override gets written here, never left as an accident.

### Script gates (check before recording)

Hook & retention (Bet 5):
- [ ] Cold open: finished app visibly working within the first ~30s — no branding/intro preamble
- [ ] "What is Vaadin?" beat is brief and defers to the existing explainer content — a beat, not a lecture
- [ ] Every chapter ends with something visibly changed on screen — no pure-setup chapter

Concept coverage (each Tier 1 concept *stated*, not just shown):
- [ ] "UI = Java objects on the server" stated in ch. 2 and reinforced as the spine of ch. 4–6
- [ ] The "how does this Java become a web page" beat is in the script — short and focused
- [ ] MainLayout beat present in ch. 4: "this frame is the starter's MainLayout — app name and nav live there" — a brief mention, *not* a build
- [ ] Listener generalization moment present ("they all look like this") — not just individual listeners
- [ ] TextField + Button → Notification scene covers components/layouts (ch. 5), then click listener + getValue (ch. 6) — one continuous scene across the chapter cut
- [ ] The "no REST layer" claim from ch. 2 is paid off *out loud* in ch. 12 (view calls the service directly — no endpoints, no DTOs, no fetch)
- [ ] One concept per chapter holds: every chapter can state its concept payload in one line (else merge/split)

Acknowledgment beats (say once, briefly, then move on — unexplained on-screen magic breeds distrust and comment noise):
- [ ] JPA annotations acknowledged when the entity class first appears as the basic data structure (ch. 7): one sentence — "ignore these annotations for now, they're how this becomes a database table — that's the database chapter" — no JPA teaching before ch. 12
- [ ] Injection acknowledged in ch. 12 when the view receives the service via its constructor: one sentence noting it's Spring at work handing the service in — not a DI lesson
- [ ] Vaadin Copilot / dev tools acknowledged at first run (ch. 3): one sentence — part of Vaadin's dev mode, ignore it for this tutorial (natural pointer to the Copilot content in next-steps)

Teaching arc & sequencing:
- [ ] Value change listener appears first in ch. 8 (filter field), not earlier
- [ ] Grid selection listener appears first in ch. 9, not earlier
- [ ] Form arc intact: ch. 9 detail population is visibly manual/tedious (`setValue()` per field); ch. 10 introduces Binder as the relief; the manual anti-pattern never reappears after ch. 10
- [ ] Ch. 11 *reuses* the ch. 9–10 form inside a Dialog (Binder + fresh bean) — no second form built for add; the form-class extraction stays a brief composition beat
- [ ] Delete + ConfirmDialog present in ch. 11 — CRUD is genuinely complete; Dialog/ConfirmDialog taught by use (~2 lines each), no API tour
- [ ] Binder stays minimal: bind fields + one validation rule + `readBean`/`writeBean` + Discard (re-`readBean`) + a one-sentence `setBean` contrast (spoken, never used on screen) — deep dive deferred to next-steps
- [ ] Backend appears first in ch. 12 — chapters 7–11 stay pure UI, no service/entity/JPA talk earlier
- [ ] Ch. 12 backend tour is a tour, not a tutorial: kept brief and focused, no JPA teaching
- [ ] The ch. 12 swap visibly changes only a few lines — a big diff kills the architecture lesson
- [ ] Ch. 13 keeps the three-part order: (1) Java-only polish (variants, spacing/positioning) → (2) theming (Aura vs Lumo, color schemes/dark mode, theme-builder paste) → (3) brief CSS beat (one CSS rule + `addClassNames(...)` example, nothing more) — and teases the theme-customization video

Audience (zero Vaadin, unknown Java level):
- [ ] No Vaadin term used before it's defined
- [ ] Lambda syntax briefly explained at first listener use; no streams/advanced generics anywhere
- [ ] Tier 3 items (Notification, FormLayout, theming) get one sentence max — no tangents

Scope (the scope decisions hold):
- [ ] Grid-extraction MAYBE resolved before recording — in (subclass, no delegation) or out (one spoken line in ch. 11); decision noted in the scope bullet
- [ ] Parking-lot ideas each resolved (in with a chapter / out / sequel) before recording — ⚠ items only in via a recorded override
- [ ] No master-detail crept back in; persistence and MainLayout stay toured/mentioned-not-built; app stays single-view
- [ ] Entity stayed flat: String/number fields only, no relations or nested objects (check `data.sql` and the form too)
- [ ] Wrap-up's next-steps names the deferred topics (Binder deep-dive, master-detail, multi-view navigation) as the natural sequels

### Edit gates (check on the cut, before publish)

- [ ] YouTube chapter markers match the 14-chapter outline (searchable chapter titles)
- [ ] Concept beats survived the edit — spot-check every concept-coverage gate above against the actual cut
- [ ] Verification pass (transcript) additionally confirms Tier 1 concepts were *spoken*, not only shown on screen

### Overrides (record cause here, per soft-gate rule)

-

## Record: first frame + transcript (from final export — see README "Recording a finished video")

Run when the final export lands in `Dropbox/Vaadin/DevRel/exports/` ("record the <slug> video").

**First frame:** [frames/<slug>-frame1.png](frames/) — one line on what it shows.

**As-shipped deltas from the planned spec** (soft-gate notes; overrides recorded with cause, not failures):
- 

**Transcript** (Whisper base.en, timestamped; ⚠ = mishears corrected — watch brand terms, "Vaadin" is often misheard):

| Time | Spoken |
|---|---|
| | |

**Verification pass:**
- [ ] Spoken CTA present and on-message
- [ ] Hook delivered as planned (first 60s for long-form)
- [ ] Verified keywords actually spoken (YouTube indexes speech)
- [ ] Duration vs. plan — note delta if any
- [ ] Caption insurance: if Whisper misheard brand terms, corrected `.srt` generated (`captions/<slug>.srt`) — upload as manual captions

## Publish

- **Date:**
- **URL:**
- **Review due:** (publish + 90d for long-form)

## Review scorecard

| Metric | Value |
|---|---|
| Goal metric (which + number) | |
| Retention (% viewed / curve notes) | |
| Production days | |
| Paid promotion? (yes/no — never compare paid vs organic) | |
| Efficiency (goal metric ÷ days) | |

**Takeaway (one line):**

**Bet update:** what does this add to the bet's tally?
