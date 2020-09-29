package com.assignment.diffapp.service;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.assignment.diffapp.enums.OutputType;
import com.assignment.diffapp.model.Difference;
import com.assignment.diffapp.model.Output;

// TODO: Auto-generated Javadoc
/**
 * The class DifferenceService. This class implements Encoded data
 * difference to compare the data.
 * 
 */
@Service
public class DifferenceService implements IDifferenceService<Output, String, String> {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DifferenceService.class);


	/**
	 * Method to compare the string.
	 *
	 * @param left the left
	 * @param right the right
	 * @return the output
	 */
	@Override
	public Output compare(String left, String right) {
		LOGGER.debug("comparing: left :{}, right:{}", left, right);
		if (left.equals(right)) {
			return new Output(OutputType.EQUALS, null);
		} else if (left.length() != right.length()) {
			return new Output(OutputType.SIZE_DIFFERENCE, null);
		} else {
			return new Output(OutputType.OFFSET_DIFFERENCE, getDiffOffset(left, right));
		}

	}

	/**
	 * Gets the difference offset for given 2 strings
	 *
	 * @param left  the left
	 * @param right the right
	 * @return the difference offset
	 */
	private List<Difference> getDiffOffset(String left, String right) {
		
		LOGGER.debug("getting different offset for left :{}, right:{}", left, right);
		List<Difference> diffList = new LinkedList<>();

		int diffLength = 0;
		int diffOffset = -1;
		int leftLength = left.length();
		int rightLength = right.length();
		for (int i = 0; i <= leftLength; i++) {
			if (i < rightLength && left.charAt(i) != right.charAt(i)) {
				//got a new difference, hence increment length and offset
				diffLength++;
				if (diffOffset < 0) {
					diffOffset = i;
				}
			} else if (diffOffset != -1) {
				//yes we got the offset, now create object and
				// put in list and reset the length and offset
				diffList.add(new Difference(diffOffset, diffLength));
				diffLength = 0;
				diffOffset = -1;
			}
		}
		return diffList;
	}

}
