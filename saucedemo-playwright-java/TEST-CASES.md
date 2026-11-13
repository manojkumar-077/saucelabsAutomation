# SauceDemo Test Case Inventory

These Java tests preserve the repository's core login, inventory, cart, and checkout scenarios.

| ID range | Class | Coverage |
|---|---|---|
| AUTH-01–AUTH-12 | `LoginTests` | Login page controls, masked password, successful login, wrong username/password, empty fields, locked user, dismiss error, logout and protected route |
| INV-01–INV-18 | `InventoryTests` | Six products, product details, four sorting modes, add/remove one or all products, open details by name/image, compare list/detail data, back navigation |
| CART-01–CART-14 | `CartTests` | Empty cart, item data, multiple items, remove one/last, badge count, continue shopping, persistence on navigation/refresh, checkout navigation, add from details |
| CHK-01–CHK-17 | `CheckoutTests` | Form controls, required fields, dismiss error, cancel paths, overview data, payment/shipping, totals for one/two items, finish order, home navigation, cart reset |

Test data accounts retained from the source: `standard_user`, `locked_out_user`, `problem_user`, `performance_glitch_user`, `error_user`, `visual_user`, plus invalid username/password combinations. Password for standard demo accounts: `secret_sauce`.

The test names in the Java classes are the executable case identifiers. The IDs above are grouping labels for documentation.
