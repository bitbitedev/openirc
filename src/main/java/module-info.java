/**
 *  Nothing to see here
 */
module dev.thatsnasu.openirc {
	exports dev.thatsnasu.openirc;
	exports dev.thatsnasu.openirc.exceptions;
	
	requires transitive dev.thatsnasu.ansi;
	requires transitive dev.bitbite.openloglib;
	requires dev.bitbite.opennetlib;
	requires io.github.classgraph;
}