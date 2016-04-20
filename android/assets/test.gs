[agenda]
    change_state talk_test
[/agenda]
[state "talk_test"]
	[main]
		delay 1
		say "...[burp]"
		delay 1
		operation memory.last_position = position
		walk_to_position world.items.lamp.position
		say "Yippie Ki Yay MotherFucker"
		delay 1
		walk_to_position memory.last_position
	[/main]
[/state]