/**
 *  Nothing to see here
 */
module dev.thatsnasu.openirc {
	exports dev.bitbite.openirc;
	exports dev.bitbite.openirc.exceptions;
	
	requires transitive dev.thatsnasu.ansi;
	requires transitive dev.bitbite.openloglib;
	requires dev.bitbite.opennetlib;
	requires io.github.classgraph;
}