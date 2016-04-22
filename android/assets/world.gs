[agenda]
    change_state exist
[/agenda]
[state "exist"]
    [main]
        // world does nothing, it just exists
        init_person bob 0:0

        init_item table -100:-100
        init_item toilet 100:-100
    [/main]
[/state]