# Discouragement Plugin
Have annoying players on your spigot minecraft server which you just can't seem to get rid of? Try this plugin!

## Commands
**Note:** Giving a player `discouragement.command.level.3` will give them the permission to use all levels of the command below 3 including 3.  
**Also Note:** Using `/discouragement lvl1` on a player with level 3 discouragement will remove the level 3 discouragement and set level 1 discouragement on the user.  
**Tip:** It is recommended that you give `discouragement.command.remove` to anyone at who has perms for any level of the command.
| Command | Description | Permissions |
|---------|-------------|-------------|
| /discouragement | Lists all discouragement commands along with their descriptions | discouragement.command.help |
| /discouragement lvl1 Username | Adds level 1 of discouragement to a given user | discouragement.command.level.1 |
| /discouragement lvl2 Username | Adds level 2 of discouragement to a given user | discouragement.command.level.2 |
| /discouragement lvl3 Username | Adds level 3 of discouragement to a given user | discouragement.command.level.3 |
| /discouragement remove Username | Removes all levels of discouragement from a given user | discouragement.command.remove |

## Discouragement levels
**Note:** Adding discouragement permissions manually using permission plugins will not make the discouragement take effect until a plugin restart. Instead use the commands to add discouragement to a user.
| Level | Description | Permission |
|-------|-------------|------------|
| 1 | Chat messages will be delayed by 0-5 seconds at random | discouragement.level.1 |
| 2 | Chat messages will be delayed by 0-15 seconds at random and will randomly tp the user to their location from 1-10 seconds prior. | discouragement.level.2 |
| 3 | Chat messages will be delayed by 0-30 seconds at random and will randomly tp the user to their location from 1-10 seconds prior. Blocks placement will fail 50% of the time as will block breaking. | discouragement.level.3 |