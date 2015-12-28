package it.polito.dp2.WF.sol2.util;

import java.util.Comparator;

import it.polito.dp2.WF.ActionStatusReader;

public class ActionStatusReaderComparator implements Comparator<ActionStatusReader> {

	@Override
	public int compare(ActionStatusReader o1, ActionStatusReader o2) {
		if( (o1.isTerminated()) && (o2.isTerminated()) )
			return o1.getTerminationTime().compareTo(o2.getTerminationTime());
		else if( (o1.isTerminated()) && (!o2.isTerminated()) )
			return -1;
		else if( (!o1.isTerminated()) && (o2.isTerminated()) )
			return 1;
		else if( (o1.isTakenInCharge()) && (!o2.isTakenInCharge()))
			return -1;
		else if( (!o1.isTakenInCharge()) && (o2.isTakenInCharge()) )
			return 1;
		else
			return 0;
	}

}
