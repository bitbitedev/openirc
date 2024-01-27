/**
 *  Nothing to see here
 */
module dev.thatsnasu.openirc {
	exports dev.thatsnasu.openirc;
	exports dev.thatsnasu.openirc.exceptions;
	
	requires transitive dev.thatsnasu.ansi;
	requires transitive dev.bitbite.openloglib;
	requires transitive OpenNetLib;
	requires transitive io.github.classgraph;
}