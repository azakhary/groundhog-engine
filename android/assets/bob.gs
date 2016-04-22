[agenda]
    change_state monolog
[/agenda]
[state "monolog"]
    [main]
        operation memory.home_pos = position
        say "okay, gotta be quick"
        say "need to eat really quick"
        say "then go to toilet and poop"
        say "yeah and one other thing"
        say "come back here!"
        say "here. we. go!"
        change_state eat
    [/main]
[/state]
[state "eat"]
    [main]
        walk_to_object world.items.table
        say "om. nom. nom"
        say "mm. yummy!"
        say "omnonononomonomnom"
        delay 2
        say "done!"
        change_state poop
    [/main]
[/state]
[state "poop"]
    [main]
        walk_to_object world.items.toilet
        say "farrrrrttttttt"
        say "ptththththththt"
        say "oh my!"
        delay 2
        say "prrt.... done!"
        change_state do_nothing
    [/main]
[/state]
[state "do_nothing"]
    [main]
        walk_to_position memory.home_pos
        say "well... bye!"
    [/main]
[/state]