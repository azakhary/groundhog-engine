[agenda]
	change_state do_nothing
[/agenda]
[state "turn_lights_on"]
	[main]
		condition_start world.light == on
			change_state do_nothing
		condition_end
		operation memory.last_position = position
		marker 1
		walk_to_position world.items.boxA.position
		operation world.light = on
		condition_start world.light == off
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
    [trigger state_tick jake.do_nothing]
        condition_start state_time > 3
            condition_start world.light != on
                say "I can't stand the dark anymore..."
                change_state turn_lights_on
            condition_end
        condition_end
    [/trigger]
	[trigger value world.light]
	    condition_start world.light == off
		    change_state turn_lights_on
		condition_end
	[/trigger]
[/state]