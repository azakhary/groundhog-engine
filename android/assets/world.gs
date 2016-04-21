[agenda]
    change_state exist
[/agenda]
[state "exist"]
    [main]
        // world does nothing, it just exists

        operation world.light = "on"

        // init people
        init_person bob -100:100
        init_person jake 0:100
        init_person phill 100:100

        // init items
        init_item boxA -50:-100
        init_item wireBox -170:-150
        init_item boxB 50:-100
    [/main]
[/state]