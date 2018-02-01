# dispatch-n-bug-mwe

A [re-frame](https://github.com/Day8/re-frame) application designed to spot and explain an behaviour I find unexpected.

After reading the code of events, I'm quite convinced that the value should be added `2` each time you click the button:

- First event `[::events/action-trigger 1]` gets `0` as `(:value db)` and adds `1` to it through `::events/action-db`, hence returning `1` as new value in the app-db.
- Second event `[::events/action-trigger 1]` gets `1` as `(:value db)` and adds `1` to it through `::events/action-db`, hence returning `2` as new value in the app-db.

However, on Chrome version `66.0.3335.0 (Official Build) canary (64-bit)` and with the dependencies versions listed in `project.clj`, the actual value after a click is `1`. Why?

## Run application

```
lein figwheel
```

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

Figwheel will automatically push cljs changes to the browser.

## Aw, found it!

It's pretty straightforward, events are *not* interleaved:

- `[::events/action]`
- `[::events/action-trigger 1]`
- `[::events/action-trigger 1]`
- `[::events/action-db 1]`
- `[::events/action-db 1]`

OK, it's explained, but it's been a bit misleading at first sight.

As a takeaway, I will remember that re-frame events are put in a queue and sequentially dispatched. Any avent is appended to the tail of the queue. It's a strong invariant. Sometimes it can be a bit misleading (like this example) but it's a good thing to rely on.
