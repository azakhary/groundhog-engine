[agenda]
	change_state do_nothing
[/agenda]
[state "turn_lights_off"]
	[main]
		condition_start world.light == off
			change_state do_nothing
		condition_end
		condition_start stats.exhaustion > 11
			change_state kick_ass
		condition_end
		operation memory.last_position = position
		marker 1
		walk_to_position world.items.boxB.position
		operation stats.exhaustion += 10
		operation world.light = off
		condition_start world.light == on
			delay 2000
			operation stats.irritation += 10
			condition_start stats.irritation > 50
				goto 2
			condition_end
			goto 1
		condition_end
		marker 2
		walk_to_position memory.last_position
		change_state do_nothing
	[/main]
[/state]
[state "do_nothing"]
    [main]
        marker start
        delay 10
        goto start
    [/main]
	[trigger value world.light == on]
	    say "Who turned lights on???"
	    delay 1
		change_state turn_lights_off
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
        walk_to_position memory.last_position
        change_state do_nothing
    [/main]
[/state]