[agenda]
    operation memory.home_position = position
	change_state do_nothing
[/agenda]
[state "turn_lights_off"]
	[main]
		condition_start world.light == off
			change_state go_home
		condition_end
		condition_start stats.exhaustion > 11
			change_state kick_ass
		condition_end
		walk_to_position world.items.boxB.position
		operation stats.exhaustion += 10
		item_interact boxB button_press
		change_state go_home
	[/main]
	[trigger value world.light]
        condition_start world.light == off
            delay 1
            say "Thank you very much, whoever it is..."
            change_state go_home
        condition_end
    [/trigger]
[/state]
[state "go_home"]
    [main]
        walk_to_position memory.home_position
        change_state do_nothing
    [/main]
    [trigger value world.light]
	    condition_start world.light == on
            say "for fucks sake...."
            delay 1
            change_state turn_lights_off
        condition_end
	[/trigger]
[/state]
[state "do_nothing"]
	[trigger value world.light]
	    condition_start world.light == on
            say "Who turned lights on???"
            delay 1
            change_state turn_lights_off
        condition_end
	[/trigger]
[/state]
[state "kick_ass"]
    [main]
        operation memory.last_position = position
        walk_to_object world.people.jake
        say "FUCK YOU FUCKING FUCK!!!!! I give up!"
        delay 1
        say "FUCKER!"
        delay 1
        change_state go_home
    [/main]
[/state]