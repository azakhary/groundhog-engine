[agenda]
    operation stats.distrust = 0
    operation stats.confess_desire = true
    operation memory.home_position = position
    operation stats.heartbroken = false
	change_state do_nothing
[/agenda]
[state "turn_lights_off"]
	[main]
		condition_start world.light == off
			change_state go_home
		condition_end
        condition_start stats.heartbroken == true
            change_state go_home
        condition_end
		operation stats.distrust += 1
		condition_start stats.hate > 25
			change_state kick_ass
		condition_end
		walk_to_position world.items.boxB.position
		item_interact boxB button_press
		operation stats.hate += 5
		condition_start stats.distrust > 2
            change_state do_nothing
        condition_end
        condition_start stats.distrust <= 2
            change_state go_home
        condition_end
	[/main]
	[trigger value world.light]
        condition_start world.light == off
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
	    condition_start world.light == on
            say "for fucks sake...."
            delay 1
            change_state turn_lights_off
        condition_end
	[/trigger]
[/state]
[state "do_nothing"]
    [main]
        delay 9
        condition_start stats.confess_desire == true
            say "Bob, I kind of love you... in a gay way"
            fire_event bob_gay_love
        condition_end
    [/main]
	[trigger value world.light]
	    condition_start world.light == on
            say "Who turned lights on???"
            delay 1
            change_state turn_lights_off
        condition_end
	[/trigger]
	[trigger event break_phill_heart]
        delay 3
        say "You broke my heart Bob...."
        operation stats.heartbroken = true
	[/trigger]
[/state]
[state "kick_ass"]
    [main]
        say "Okay that's it, you're dead you lil bitch!"
        operation stats.confess_desire = false
        operation memory.last_position = position
        walk_to_object world.people.jake
        say "FUCK YOU FUCKING FUCK!!!!! SMACK!"
        delay 1
        operation world.people.jake.stats.health -= 10
        say "FUCKER!"
        operation stats.hate = 0
        operation stats.distrust = 0
        delay 1
        change_state turn_lights_off
    [/main]
[/state]