[agenda]
    operation stats.health = 9
    operation stats.not_working = false
    operation memory.home_position = position
	change_state do_nothing
[/agenda]
[state "turn_lights_on"]
	[main]
		condition_start world.light == on
			change_state do_nothing
		condition_end
		condition_start stats.not_working == true
            change_state do_nothing
        condition_end
		condition_start stats.health <= 0
		    say "okay just don't hit me anymore"
			change_state do_nothing
		condition_end
		say "I can't stand the dark anymore..."
		operation memory.last_position = position
		walk_to_position world.items.boxA.position
		item_interact boxA button_press
		condition_start world.light == off
		    operation stats.not_working = true
        	change_state go_home
        condition_end
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
    [trigger value stats.health]
        condition_start stats.health <= 0
            say "ouch! :("
            delay 1
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
    [trigger value stats.health]
        condition_start stats.health <= 0
            say "ouch! :("
            delay 1
            change_state go_home
        condition_end
    [/trigger]
[/state]
[state "do_nothing"]
    [trigger state_tick jake.do_nothing]
        condition_start state_time > 2
            condition_start world.light != on
                condition_start stats.health > 0
                    change_state turn_lights_on
                condition_end
            condition_end
        condition_end
    [/trigger]
	[trigger value world.light]
	    condition_start world.light == off
	        condition_start stats.health > 0
		        change_state turn_lights_on
		    condition_end
		condition_end
	[/trigger]
    [trigger value stats.health]
        condition_start stats.health <= 0
            say "ouch! :("
            delay 1
            change_state go_home
        condition_end
    [/trigger]
[/state]