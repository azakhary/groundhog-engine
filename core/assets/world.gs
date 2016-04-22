[agenda]
    operation world.qfurs = 0
    change_state exist
[/agenda]
[state "exist"]
    [main]
        // world does nothing, it just exists
        init_person hendo -100:100
        init_person jrmo 0:100
        init_person hrle 100:100
        init_person hermione 0:-100

        init_item toilet 0:0
        init_item table 0:-170
    [/main]
    [trigger event qfur_done]
        operation world.qfurs += 1
    [/trigger]
[/state]