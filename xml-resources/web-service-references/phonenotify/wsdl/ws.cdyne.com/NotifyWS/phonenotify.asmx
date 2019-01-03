

<html>

    <head><link rel="alternate" type="text/xml" href="/NotifyWS/phonenotify.asmx?disco" />

    <style type="text/css">
    
		BODY { color: #000000; background-color: white; font-family: Verdana; margin-left: 0px; margin-top: 0px; }
		#content { margin-left: 30px; font-size: .70em; padding-bottom: 2em; }
		A:link { color: #336699; font-weight: bold; text-decoration: underline; }
		A:visited { color: #6699cc; font-weight: bold; text-decoration: underline; }
		A:active { color: #336699; font-weight: bold; text-decoration: underline; }
		A:hover { color: cc3300; font-weight: bold; text-decoration: underline; }
		P { color: #000000; margin-top: 0px; margin-bottom: 12px; font-family: Verdana; }
		pre { background-color: #e5e5cc; padding: 5px; font-family: Courier New; font-size: x-small; margin-top: -5px; border: 1px #f0f0e0 solid; }
		td { color: #000000; font-family: Verdana; font-size: .7em; }
		h2 { font-size: 1.5em; font-weight: bold; margin-top: 25px; margin-bottom: 10px; border-top: 1px solid #003366; margin-left: -15px; color: #003366; }
		h3 { font-size: 1.1em; color: #000000; margin-left: -15px; margin-top: 10px; margin-bottom: 10px; }
		ul { margin-top: 10px; margin-left: 20px; }
		ol { margin-top: 10px; margin-left: 20px; }
		li { margin-top: 10px; color: #000000; }
		font.value { color: darkblue; font: bold; }
		font.key { color: darkgreen; font: bold; }
		font.error { color: darkred; font: bold; }
		.heading1 { color: #ffffff; font-family: Tahoma; font-size: 26px; font-weight: normal; background-color: #003366; margin-top: 0px; margin-bottom: 0px; margin-left: -30px; padding-top: 10px; padding-bottom: 3px; padding-left: 15px; width: 105%; }
		.button { background-color: #dcdcdc; font-family: Verdana; font-size: 1em; border-top: #cccccc 1px solid; border-bottom: #666666 1px solid; border-left: #cccccc 1px solid; border-right: #666666 1px solid; }
		.frmheader { color: #000000; background: #dcdcdc; font-family: Verdana; font-size: .7em; font-weight: normal; border-bottom: 1px solid #dcdcdc; padding-top: 2px; padding-bottom: 2px; }
		.frmtext { font-family: Verdana; font-size: .7em; margin-top: 8px; margin-bottom: 0px; margin-left: 32px; }
		.frmInput { font-family: Verdana; font-size: 1em; }
		.intro { margin-left: -15px; }
           
    </style>

    <title>
	PhoneNotify Web Service
</title></head>

  <body>

    <div id="content">

      <p class="heading1">PhoneNotify</p><br>

      <span>
          <p class="intro">This service is FREE to test.  The system will attempt to call a number 1 time until it is answered (Can be modified with the TryCount property).<br>We now accept Extension numbers by using an 'x' in the phone number (ex:555-555-1000x3025).<br><br>A reminder about abuse:  <b>Threats and illegal activity can be shared with the authorities.</b><p> For Advanced TextToSay Commands Visit: <a href="http://wiki.cdyne.com/index.php/Notify_TextToSay_Commands">http://wiki.cdyne.com/index.php/Notify_TextToSay_Commands</a></p></p>
      </span>

      <span>

          <p class="intro">The following operations are supported.  For a formal definition, please review the <a href="phonenotify.asmx?WSDL">Service Description</a>. </p>
          
          
              <ul>
            
              <li>
                <a href="phonenotify.asmx?op=AssignIncomingNumber">AssignIncomingNumber</a>
                
                <span>
                  <br>This method will assign a Incoming Number to a License Key.  Returns true if assignment is successful.  Returns false if the number is claimed or not available.  Numbers are billed at $5/month per number.  Normal fees for transactions apply.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=CancelConference">CancelConference</a>
                
                <span>
                  <br>Cancels/Kills a Conference by a Conference Key
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=CancelNotify">CancelNotify</a>
                
                <span>
                  <br>Cancels a single notify.  This will not cancel completed calls or calls in progress.  You will credited for any successfully cancelled notify that returns a true.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=CancelNotifyByReferenceID">CancelNotifyByReferenceID</a>
                
                <span>
                  <br>Cancels a batch notify by ReferenceID.  This will not cancel completed calls or calls in progress.  You will credited for any successfully cancelled notifies and that number will return greater than zero.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetAssignedNumbers">GetAssignedNumbers</a>
                
                <span>
                  <br>Gets a list of Incoming Numbers assigned to a LicenseKey
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetAvailableAreaCodes">GetAvailableAreaCodes</a>
                
                <span>
                  <br>This method returns all the available area codes in our system.  Please contact info@cdyne.com if you need more listed.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetAvailableIncomingNumbers">GetAvailableIncomingNumbers</a>
                
                <span>
                  <br>This method will list a random 10 lines available for incoming calls.  You can assign these lines via the AssignIncomingNumber method to your license key.  You can leave the area code field blank to get any available toll-free lines (888 and 877 numbers are currently the most common).
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetIncomingCallScript">GetIncomingCallScript</a>
                
                <span>
                  <br>Allows you to get the Call Script for incoming calls for a particular number.  You must have incoming phone numbers set by CDYNE Staff.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetMultipleQueueIdStatus">GetMultipleQueueIdStatus</a>
                
                <span>
                  <br>This Method returns information about the status of multiple notifies via queueid (delimited by a semicolon).  This transaction is free, but does require a valid licensekey.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetQueueIDStatus">GetQueueIDStatus</a>
                
                <span>
                  <br>Returns a status on a particular QueueID.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetQueueIDStatusWithAdvancedInfo">GetQueueIDStatusWithAdvancedInfo</a>
                
                <span>
                  <br>Returns a status on a particular QueueID.  This also includes Variable information and more.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetQueueIDStatusesByPhoneNumber">GetQueueIDStatusesByPhoneNumber</a>
                
                <span>
                  <br>Returns the last 10 phone notifications for a particular phone number.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetResponseCodes">GetResponseCodes</a>
                
                <span>
                  <br>This method will list all of the Response Codes.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetSoundFile">GetSoundFile</a>
                
                <span>
                  <br>Sound file Retrieval. (free)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetSoundFileInMP3">GetSoundFileInMP3</a>
                
                <span>
                  <br>Sound file Retrieval (returns an MP3 encoded in 32,64, or 128). (free)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetSoundFileInUlaw">GetSoundFileInUlaw</a>
                
                <span>
                  <br>Sound file Retrieval (returns ULAW encoded file). (free)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetSoundFileLength">GetSoundFileLength</a>
                
                <span>
                  <br>Gets a Sound File length in Seconds. (free)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetSoundFileURL">GetSoundFileURL</a>
                
                <span>
                  <br>Sound file Retrieval.  Returns a URL to Listen to a particular Sound File in MP3 (free)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetTTSInMP3">GetTTSInMP3</a>
                
                <span>
                  <br>Allows you to convert text into a MP3 encoded sound file. (returns an MP3 encoded in 32,64, or 128). (May require a additional License Key)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetTTSInULAW">GetTTSInULAW</a>
                
                <span>
                  <br>Allows you to convert text into a ULAW encoded sound file.  (May require an additional License Key)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=GetVersion">GetVersion</a>
                
                <span>
                  <br>This method returns CDYNE Version information
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LM_AddListMember">LM_AddListMember</a>
                
                <span>
                  <br>List Management - Allows you to add contacts to a List.  You will need the listid.  The only required fields are ListID, PhoneNumber, and LicenseKey.  Returns a List Member ID.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LM_AddNewList">LM_AddNewList</a>
                
                <span>
                  <br>List Management - Allows you to add a list that can contain members (Contacts).  You can also attach this list to be apart of a Parent List, otherwise leave ParentList ID = 0.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LM_AlterListID">LM_AlterListID</a>
                
                <span>
                  <br>List Management - Allows you alter a ListMember, use 0 if you do not want to have a ParentListID, and -1 if you do not wish to change the ParentListID
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LM_AlterListMember">LM_AlterListMember</a>
                
                <span>
                  <br>List Management - Allows you alter a ListMember
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LM_DeleteList">LM_DeleteList</a>
                
                <span>
                  <br>List Management - Deletes a List and it's sub-lists (The lists that have is as a ParentListID).
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LM_DeleteListMember">LM_DeleteListMember</a>
                
                <span>
                  <br>List Management - Deletes a List's Member (or contact)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LM_DialList">LM_DialList</a>
                
                <span>
                  <br>List Management - Dial a List by ListID.  Returns a Batch ID for the list and begins dialing at the scheduled time.  Scheduled times are in UTC. For more information visit: <a href="http://wiki.cdyne.com/wiki/index.php?title=Phone_Notify%21_LM_DialList">http://wiki.cdyne.com/wiki/index.php?title=Phone_Notify%21_LM_DialList</a>
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LM_DialListAdvanced">LM_DialListAdvanced</a>
                
                <span>
                  <br>List Management - Dial a list by ListID that allows you to set more advanced properties.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LM_GetListIDsByLicensekey">LM_GetListIDsByLicensekey</a>
                
                <span>
                  <br>List Management - Obtains all the list IDs from a specific License key.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LM_GetListMembersByListID">LM_GetListMembersByListID</a>
                
                <span>
                  <br>List Management - Gets all the phone numbers with their first and last names.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LicenseKeyVariableLoad">LicenseKeyVariableLoad</a>
                
                <span>
                  <br>Variable Management - Load a key's variable (The Variable name can be up to 50 characters long).
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=LicenseKeyVariableSave">LicenseKeyVariableSave</a>
                
                <span>
                  <br>Variable Management - Save a variable (VariableName can only be 50 characters or less).  Saving a Variable with an existing name will overwrite the old Variable.  Saving a blank value will delete the variable.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=NotifyMultiplePhoneAdvanced">NotifyMultiplePhoneAdvanced</a>
                
                <span>
                  <br>Allows you to control the notifies with a class.  This Function allows Multiple Notifies to be sent in one send.  This allows for the maximum combinations of using notify.  We suggest using our list management features for Notify batches over 100.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=NotifyMultiplePhoneBasic">NotifyMultiplePhoneBasic</a>
                
                <span>
                  <br>This Method will call phone numbers with a default of 3000 calls per minute. (delimited by a semicolon) in the US/Canada and read the TextToSay to that phone number.  You must use a license key for this function.  Your billing will be charged per phone number.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=NotifyMultiplePhoneBasicWithCPM">NotifyMultiplePhoneBasicWithCPM</a>
                
                <span>
                  <br>This Method will call phone numbers with a way to set calls per minute. (delimited by a semicolon) in the US/Canada and read the TextToSay to that phone number.  You must use a license key for this function.  Your billing will be charged per phone number.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=NotifyMultiplePhoneBasicWithCPMandReferenceID">NotifyMultiplePhoneBasicWithCPMandReferenceID</a>
                
                <span>
                  <br>This Method will call phone numbers with a way to set calls per minute. (delimited by a semicolon) in the US/Canada and read the TextToSay to that phone number.  You must use a license key for this function.  Your billing will be charged per phone number.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=NotifyPhoneAdvanced">NotifyPhoneAdvanced</a>
                
                <span>
                  <br>Allows you to control the notifies with a class.  This allows for the maximum combinations of using notify.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=NotifyPhoneBasic">NotifyPhoneBasic</a>
                
                <span>
                  <br>This Method will call any phone number in the US/Canada and read the TextToSay to that phone number. <br>Set VoiceID equal to 0 for TTS Diane to speak the Text.  For a list of Voices with their ID look at getVoices. PhoneNumberToDial and CallerID must be filled in (They can be in any format as long as there is 10 digits).<br>A reminder about abuse:  <b>Threats and illegal activity can be shared with the authorities.</b><br>Use a LicenseKey of 0 for testing.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=NotifyPhoneBasicWithTransfer">NotifyPhoneBasicWithTransfer</a>
                
                <span>
                  <br><b>You must have a valid CDYNE key to use this.  Please test with our other methods.</b>  This Method is exactly like the NotifyPhoneBasic with one exception.  It allows you to transfer a call by pressing 0.  Put the Transfer phone number in TransferNumberToOnDigit.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=NotifyPhoneBasicWithTryCount">NotifyPhoneBasicWithTryCount</a>
                
                <span>
                  <br>Same as NotifyPhoneBasic with a number of times it will retry the call.  The max is 3.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=NotifyPhoneEnglishBasic">NotifyPhoneEnglishBasic</a>
                
                <span>
                  <br>This Method will call any phone number in the US/Canada and read the TextToSay to that phone number using the voice of Diane (voiceid: 0). PhoneNumberToDial must be filled in (They can be in any format as long as there is 10 digits).<br>A reminder about abuse:  <b>Threats and illegal activity can be shared with the authorities.</b><br>Use a LicenseKey of 0 for testing.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=RecordSoundViaPhoneCall">RecordSoundViaPhoneCall</a>
                
                <span>
                  <br>Request the system to call you to record a sound file. SoundfileIDs can only be lowercase and contain normal file characters.  The CallerIDName will be the SoundFileID and the number will be 8000000000.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=RemoveSoundFile">RemoveSoundFile</a>
                
                <span>
                  <br>Sound file delete. (free)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=RenameSoundFile">RenameSoundFile</a>
                
                <span>
                  <br>Sound file rename. (free)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=ReturnSoundFileIDs">ReturnSoundFileIDs</a>
                
                <span>
                  <br>Get available sound files that you can add to your stream via a license key. (free)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=ScriptDelete">ScriptDelete</a>
                
                <span>
                  <br>Script Management - Delete a script
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=ScriptList">ScriptList</a>
                
                <span>
                  <br>Script Management - Lists the scripts belonging to a license key.  Set IncludeGlobalScripts to true if you would like CDYNE global scripts to be included.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=ScriptLoad">ScriptLoad</a>
                
                <span>
                  <br>Script Management - Load a script's text
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=ScriptSave">ScriptSave</a>
                
                <span>
                  <br>Script Management - Save a script (Scriptname can only be 50 characters or less).  Saving a script with an existing name will overwrite the old script.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=SetIncomingCallScript">SetIncomingCallScript</a>
                
                <span>
                  <br>Allows you to update a Call Script for incoming calls.  You must have incoming phone numbers set by CDYNE Staff.
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=UploadSoundFile">UploadSoundFile</a>
                
                <span>
                  <br>Sound file upload.  Our system accepts WAV (PCM, U-law, A-law, MS ADPCM) Files.  We plan on adding more very soon.<br>To use the sound file in the notify put ~ around it and start it with a ^.Example "Hello ~^soundfile~, you are cool."(This would text to speech Hello and you are cool,adding the sound file in the middle). (free)
                </span>
              </li>
              <p>
            
              <li>
                <a href="phonenotify.asmx?op=getVoices">getVoices</a>
                
                <span>
                  <br>This method will list all the voices available for your notification. Use the VoiceID for the Notification Methods
                </span>
              </li>
              <p>
            
              </ul>
            
      </span>

      
      

    <span>
        
    </span>
    
      

      

    
  </body>
</html>
