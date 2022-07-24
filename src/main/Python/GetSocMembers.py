import os
import requests

from dotenv import load_dotenv

load_dotenv()

URL = os.getenv("UNION_URL")
COOKIE = os.getenv("UNION_COOKIE")


def get_soc_members():
    """
    Get the list of members of the society
    """
    