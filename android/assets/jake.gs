[agenda]
    operation memory.home_position = position
	change_state do_nothing
[/agenda]
[state "turn_lights_on"]
	[main]
		condition_start world.light == on
			change_state do_nothing
		condition_end
		operation memory.last_position = position
		walk_to_position world.items.boxA.position
		item_interact boxA button_press
		change_state go_home
	[/main]
	[trigger value world.light]
        condition_start world.light == on
            delay 1
            say "Oh.. okay then.."
            walk_to_position memory.last_position
            change_state do_nothing
        condition_end
    [/trigger]
[/state]
[state "go_home"]
    [main]
        walk_to_position memory.home_position
        change_state do_nothing
    [/main]
    [trigger value world.light]
	    condition_start world.light == off
            say "dude.."
            delay 1
            change_state turn_lights_on
        condition_end
        condition_start world.light == on
            walk_to_position memory.home_position
            change_state do_nothing
        condition_end
	[/trigger]
[/state]
[state "do_nothing"]
    [trigger state_tick jake.do_nothing]
        condition_start state_time > 2
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