/* ====================================================================
 * Limited Evaluation License:
 *
 * The exclusive owner of this work is the OpenRate project.
 * This work, including all associated documents and components
 * is Copyright of the OpenRate project 2006-2013.
 *
 * The following restrictions apply unless they are expressly relaxed in a
 * contractual agreement between the license holder or one of its officially
 * assigned agents and you or your organisation:
 *
 * 1) This work may not be disclosed, either in full or in part, in any form
 *    electronic or physical, to any third party. This includes both in the
 *    form of source code and compiled modules.
 * 2) This work contains trade secrets in the form of architecture, algorithms
 *    methods and technologies. These trade secrets may not be disclosed to
 *    third parties in any form, either directly or in summary or paraphrased
 *    form, nor may these trade secrets be used to construct products of a
 *    similar or competing nature either by you or third parties.
 * 3) This work may not be included in full or in part in any application.
 * 4) You may not remove or alter any proprietary legends or notices contained
 *    in or on this work.
 * 5) This software may not be reverse-engineered or otherwise decompiled, if
 *    you received this work in a compiled form.
 * 6) This work is licensed, not sold. Possession of this software does not
 *    imply or grant any right to you.
 * 7) You agree to disclose any changes to this work to the copyright holder
 *    and that the copyright holder may include any such changes at its own
 *    discretion into the work
 * 8) You agree not to derive other works from the trade secrets in this work,
 *    and that any such derivation may make you liable to pay damages to the
 *    copyright holder
 * 9) You agree to use this software exclusively for evaluation purposes, and
 *    that you shall not use this software to derive commercial profit or
 *    support your business or personal activities.
 *
 * This software is provided "as is" and any expressed or impled warranties,
 * including, but not limited to, the impled warranties of merchantability
 * and fitness for a particular purpose are disclaimed. In no event shall
 * Tiger Shore Management or its officially assigned agents be liable to any
 * direct, indirect, incidental, special, exemplary, or consequential damages
 * (including but not limited to, procurement of substitute goods or services;
 * Loss of use, data, or profits; or any business interruption) however caused
 * and on theory of liability, whether in contract, strict liability, or tort
 * (including negligence or otherwise) arising in any way out of the use of
 * this software, even if advised of the possibility of such damage.
 * This software contains portions by The Apache Software Foundation, Robert
 * Half International.
 * ====================================================================
 */

package OpenRate.record;

/**
 * Errors are added to the record at any time during processing.
 */
public class RecordError extends AbstractError
{
  /**
   * CVS version info - Automatically captured and written to the Framework
   * Version Audit log at Framework startup. For more information
   * please <a target='new' href='http://www.open-rate.com/wiki/index.php?title=Framework_Version_Map'>click here</a> to go to wiki page.
   */
  public static String CVS_MODULE_INFO = "OpenRate, $RCSfile: RecordError.java,v $, $Revision: 1.16 $, $Date: 2013-05-13 18:12:11 $";

  private static final long serialVersionUID = -5050358890913890453L;

 /**
  * Constructor for RecordError, providing the type as well as the message and
  * the module name
  *
  * @param msg The message to attach to the error
  * @param type The error type
  * @param moduleName The name of the calling module
  */
  public RecordError(String msg, ErrorType type, String moduleName, String errorDescription)
  {
    super(msg, type, moduleName, errorDescription);
  }

 /**
  * Constructor for RecordError, providing the type as well as the message and
  * the module name
  *
  * @param msg The message to attach to the error
  * @param type The error type
  * @param moduleName The name of the calling module
  */
  public RecordError(String msg, ErrorType type, String moduleName)
  {
    super(msg, type, moduleName);
  }

 /**
  * Constructor for RecordError, providing the type as well as the message
  *
  * @param msg The message to attach to the error
  * @param type The error type
  */
  public RecordError(String msg, ErrorType type)
  {
    super(msg, type);
  }

  /**
   * Constructor for RecordError, uses the default special type
   *
   * @param msg The message to attach to the error. The type will be "SPECIAL"
   */
  public RecordError(String msg)
  {
    super(msg, ErrorType.SPECIAL);
  }
}