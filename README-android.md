# LitByLeia Fork of Citra for Android

## Changelog

- See [releases](https://github.com/jakedowns/citra-android/releases) page for .apk files

### Beta 2021.09.09
- added option to adjust overlay control opacity [Issue #245](https://github.com/citra-emu/citra-android/issues/245)
- added option to enable & adjust haptic feedback for on screen controls (Thanks to [@VitorTrin](https://github.com/citra-emu/citra-android/pull/212) & [@JetPlaneJJ](https://github.com/citra-emu/citra-android/issues/226#issuecomment-895529118))
- added option to enable sliding finger between face buttons [Issue #247](https://github.com/citra-emu/citra-android/issues/247), similar to how DPad behaves (Thanks to [@nathanlepori](https://github.com/citra-emu/citra-android/pull/72))

### Beta 15.1
- add support for Lit By Leia stereo displays

# Android Performance Ideas

### Asynchronous Shader Compilation?
💡 asynchronous shader compilation is probably a bad idea, tiered shader compilation though…… lots of effort
> The idea is that if you have two compilers, one fast at compiling but generates slow code, and one slow at compiling but generates fast code,
> you can use the first and replace it with the second when it completes.
> Citra has a shader compiler that runs shaders on the CPU
> which could be used as a first tier
> and the slower hardware shaders would be the second tier
> This of course assumes that shaders are the issue to begin with
> @merry on [#development discord chat]

## Android Devices can be notoriously bad about throttling. What have we tried to prevent CPU from throttling?
> See: [https://discord.com/channels/220740965957107713/878088629455573002/878322621765287947]()

### Copy Drastic's trick to keep thread on BIG core
> We've looked into doing what drastic does: keep the process running intense math instructions to keep it on a hot core. Iirc it didn't help because Citra is already heavy enough.
> @BreadFish64
--
> drastic's math thing was designed to trick classic big.LITTLE with only 2 core clusters (that's what I have on my phone), but in Qualcomm's mess of prime/gold/silver cores, it would seem we need to increase the load on the JIT thread itself so that thread stays scheduled on the correct core at the correct frequencies.
> @xperia64

### How about `setSustainedPerformanceMode`?
💡 [Android Source > AOSP > Configure > Power > Performance Management > Sustained Performance](https://source.android.com/devices/tech/power/performance)
> at least on Snapdragon 845 (Lume Pad) enabling this option doesn't seem to help
> TODO could at least add it as a configurable option for users to test on other chipsets

### Have we tried `setThreadPriority`?
> setThreadPriority won't help unprivileged applications aren't allowed to set niceness < 0, and Android apparently will already give you -2 when you're the foreground app.
> @xperia64 on [#development discord chat]

### Allow Frame Skip?
Q: could we enable frameskipping for android? 30fps locked is better than stuttery 40-60fps
> We can't do frame skipping reliably because the 3DS renders to arbitrary frame buffers in memory. The DS was probably the last console you could get away with that.
> @BreadFish64 on [#development discord chat](https://discord.com/channels/220740965957107713/343888608442449920/879094731878072491)